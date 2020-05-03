package com.uem.google.bigquery.main;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.*;
import com.google.api.services.bigquery.model.DatasetList.Datasets;
import com.uem.model.User;
import com.uem.util.GAuthenticate;
import com.uem.util.LogUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AllBQOperations {

    private static final String PROJECT_ID = "universalevaluationmetrics";
    private static final String DATASET_ID = "universalEvaluationMetrics";

    public static String toString_() {
        return "AllBQOperations{PROJECT_ID:" + PROJECT_ID + ", DATASET_ID:" + DATASET_ID + "}";
    }

    static Logger logger = LogUtil.getInstance();

    public static List<User> getAllUsers(String email) {

        String PROJECT_ID = "universalevaluationmetrics";

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String querySql = "SELECT\n" +
                "  UserID,\n" +
                "  Email,\n" +
                "  Password,\n" +
                "  Name,\n" +
                "  Mobile,\n" +
                "  Photo\n" +
                "FROM\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.User`";

        if (email != null) {
            querySql = "SELECT\n" +
                "  UserID,\n" +
                "  Email,\n" +
                "  Password,\n" +
                "  Name,\n" +
                "  Mobile,\n" +
                "  Photo\n" +
                "FROM\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.User`\n" +
                "WHERE\n" +
                "  Email LIKE '%" + email + "%'";
        }
        JobReference jobId = null;
        Job completedJob = null;
        ArrayList<User> users = null;
        try {
            jobId = startQuery(bigquery, PROJECT_ID, querySql);
            if (jobId != null) {
                completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);
                if (completedJob != null) {
                    users = new ArrayList<User>(
                            getUsers(bigquery, PROJECT_ID, completedJob));
                }
            }

        } catch (Exception e) {
            // logger.error(e);
            e.printStackTrace();
        } finally {
            jobId = null;
            completedJob = null;
        }
        return users;
    }

    private static ArrayList<User> getUsers(Bigquery bigquery, String projectId,
                                                                 Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = bigquery.jobs()
                .getQueryResults(projectId, completedJob.getJobReference().getJobId()).execute();
        List<TableRow> rows = queryResult.getRows();
        ArrayList<User> users = new ArrayList<User>();
        System.out.print("\nQuery Results:\n------------\n");

        if (rows != null) {
            for (TableRow row : rows) {
                LinkedList<TableCell> rowList = new LinkedList<TableCell>();
                rowList.addAll(row.getF());
                User user = new User();
                user.setUserID(rowList.get(0).getV().toString());
                user.setEmail(rowList.get(1).getV().toString());
                user.setPassword(rowList.get(2).getV().toString());
                user.setName(rowList.get(3).getV().toString());
                user.setMobile(rowList.get(4).getV().toString());
                user.setPhoto(rowList.get(5).getV().toString());
                users.add(user);
            }
        }

        return users;

    }

    public static JobReference startQuery(Bigquery bigquery, String projectId, String querySql) {
        String status = "";
        JobReference jobId = null;
        for (int i = 0; i < 4; i++) {
            try {
                System.out.format("\nInserting Query Job: %s\n", querySql);

                Job job = new Job();
                JobConfiguration config = new JobConfiguration();
                JobConfigurationQuery queryConfig = new JobConfigurationQuery();
                queryConfig.setUseLegacySql(false);
                config.setQuery(queryConfig);

                job.setConfiguration(config);
                queryConfig.setQuery(querySql);

                Bigquery.Jobs.Insert insert = bigquery.jobs().insert(projectId, job);
                insert.setProjectId(projectId);
                jobId = insert.execute().getJobReference();
                status = "ok";
                System.out.format("\nJob ID of Query Job is: %s\n", jobId.getJobId());
                if (jobId != null && status == "ok") {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e1) {
                    // TODO Auto-genaerated catch block
                    e1.printStackTrace();
                }
            }
        }
        return jobId;
    }

    private static Job checkQueryResults(Bigquery bigquery, String projectId, JobReference jobId)
            throws IOException, InterruptedException {
        // Variables to keep track of total query time
        long startTime = System.currentTimeMillis();
        long elapsedTime;

        while (true) {
            Job pollJob = bigquery.jobs().get(projectId, jobId.getJobId()).execute();
            elapsedTime = System.currentTimeMillis() - startTime;
            System.out.format("Job status (%dms) %s: %s\n", elapsedTime, jobId.getJobId(),
                    pollJob.getStatus().getState());
            if (pollJob.getStatus().getState().equals("DONE")) {
                return pollJob;
            }
            // Pause execution for one second before polling job status again,
            // to
            // reduce unnecessary calls to the BigQUery API and lower overall
            // application bandwidth.
            Thread.sleep(1000);
        }
    }

    public static Boolean createAllTable(Bigquery bigquery) {

        BQTable_Batch.createTable(bigquery, true, true);
        BQTable_Course.createTable(bigquery, true, true);
        BQTable_CourseAdmin.createTable(bigquery, true, true);
        BQTable_Permissions.createTable(bigquery, true, true);
        BQTable_Student.createTable(bigquery, true, true);
        BQTable_Teacher.createTable(bigquery, true, true);
        BQTable_UnivAdmin.createTable(bigquery, true, true);
        BQTable_University.createTable(bigquery, true, true);
        BQTable_User.createTable(bigquery, true, true);
        return true;
    }

    public static Boolean createDataset(Bigquery bigquery) {

        try {

            DatasetList datasets = bigquery.datasets().list(PROJECT_ID).setMaxResults(1000L).execute();

            Boolean datasetCreate = true;

            for (Datasets dataset : datasets.getDatasets()) {

                if (dataset.getId().equalsIgnoreCase(PROJECT_ID + ":" + DATASET_ID)) {

                    datasetCreate = false;

                }

            }

            if (datasetCreate) {
                Dataset datasetresponse = bigquery.datasets().insert(PROJECT_ID, new Dataset().setId(DATASET_ID).setKind("bigquery#dataset").setDatasetReference(new DatasetReference().setProjectId(PROJECT_ID).setDatasetId(DATASET_ID))).execute();
                logger.debug(datasetresponse);
                return true;
            } else {
                logger.debug("Response Message : Dataset is already created in Big Query.");
                return true;
            }

        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

    public static Boolean StructureValidate(Bigquery bigquery) {

        try {

            if (createDataset(bigquery)) {

                if (createAllTable(bigquery)) {

                    return true;

                } else {
                    logger.debug("Response Message : Couldn't create Table. Returning Back with FALSE status.");
                    return false;
                }

            } else {
                logger.debug("Response Message : Couldn't find Dataset. Returning Back with FALSE status.");
                return false;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;

        }

    }

}