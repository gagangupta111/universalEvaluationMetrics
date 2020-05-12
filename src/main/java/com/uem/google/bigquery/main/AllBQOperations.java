package com.uem.google.bigquery.main;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.*;
import com.google.api.services.bigquery.model.DatasetList.Datasets;
import com.uem.model.*;
import com.uem.util.GAuthenticate;
import com.uem.util.LogUtil;
import com.uem.util.UtilsManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class AllBQOperations {

    private static final String PROJECT_ID = "universalevaluationmetrics";
    private static final String DATASET_ID = "universalEvaluationMetrics";

    public static String toString_() {
        return "AllBQOperations{PROJECT_ID:" + PROJECT_ID + ", DATASET_ID:" + DATASET_ID + "}";
    }

    static Logger logger = LogUtil.getInstance();

    public static  Boolean updateUser(User user){

        List<String> updates = new ArrayList<>();
        if (user.getMobile() != null){
            updates.add("Mobile = \"" + user.getMobile() + "\"");
        }
        if (user.getName() != null){
            updates.add("Name = \"" + user.getName() + "\"");
        }
        if (user.getAddress() != null){
            updates.add("Address = \"" + user.getAddress() + "\"");
        }
        if (user.getDOB() != null){
            updates.add("DOB = \"" + user.getDOB() + "\"");
        }
        if (user.getPhoto() != null){
            updates.add("Photo = " + user.getPhoto() + "");
        }

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);
        String querySql = "UPDATE\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.User`\n" +
                "SET ";
        String endQuery = " WHERE\n" +
                "  UserID = \"" + user.getUserID() + "\"";

        String update = String.join(",", updates);
        querySql = querySql + update + endQuery;

        JobReference jobId = null;
        Job completedJob = null;
        try {
            jobId = startQuery(bigquery, PROJECT_ID, querySql);
            if (jobId != null) {
                completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);
                if (completedJob != null) {
                    Boolean aBoolean = updatesResult(bigquery, PROJECT_ID, completedJob);
                    return aBoolean;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobId = null;
            completedJob = null;
        }
        return false;
    }

    public static  Map<String, Object> createUser(String email){
        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String UserID = UtilsManager.generateUniqueID();
        String Email = email;
        String Password = UtilsManager.generateUniqueID();
        String UEM_ID = UtilsManager.generateUniqueID();

        ArrayList<TableDataInsertAllRequest.Rows> datachunk =
                new ArrayList<TableDataInsertAllRequest.Rows>();
        TableDataInsertAllRequest.Rows row = new TableDataInsertAllRequest.Rows();
        Map<String, Object> data = new HashMap<>();
        data.put("UserID", UserID);
        data.put("Email", Email);
        data.put("Password", Password);
        row.setJson(data);
        datachunk.add(row);
        Boolean aBoolean = BQTable_User.insertDataRows(bigquery, datachunk);
        if (aBoolean){
            datachunk =
                    new ArrayList<TableDataInsertAllRequest.Rows>();
            row = new TableDataInsertAllRequest.Rows();
            data = new HashMap<>();
            data.put("UserID", UserID);
            data.put("UEM_ID", UEM_ID);
            row.setJson(data);
            datachunk.add(row);
            aBoolean = BQTable_UnivAdmin.insertDataRows(bigquery, datachunk);
            if (aBoolean){
                data = new HashMap<>();
                data.put("UserID", UserID);
                data.put("Email", Email);
                data.put("Password", Password);
                data.put("UEM_ID", UEM_ID);
                return data;
            }
        }
        return data;
    }

    public static Boolean updateUniversity(University university) throws JSONException {

        List<String> updates = new ArrayList<>();
        if (university.getName() != null){
            updates.add("Name = \"" + university.getName() + "\"");
        }
        if (university.getPhoto() != null){
            updates.add("Photo = \"" + university.getPhoto() + "\"");
        }
        if (university.getUnivAdmins() != null){
            updates.add("UnivAdmins = \"" + university.getUnivAdmins() + "\"");
        }
        if (university.getStudents() != null){
            updates.add("Students = \"" + university.getStudents() + "\"");
        }
        if (university.getTeachers() != null){
            updates.add("Teachers = \"" + university.getTeachers() + "\"");
        }
        if (university.getCourses() != null){
            updates.add("Courses = \"" + university.getCourses() + "\"");
        }
        if (university.getWebsite() != null){
            updates.add("Website = \"" + university.getWebsite() + "\"");
        }
        if (university.getMoreInfo() != null){
            updates.add("MoreInfo = \"" + university.getMoreInfo() + "\"");
        }
        if (university.getActionLogs() != null){
            updates.add("ActionLogs = \"" + university.getActionLogs() + "\"");
        }

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);
        String querySql = "UPDATE\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.University`\n" +
                "SET ";
        String endQuery = " WHERE\n" +
                "  UnivID = \"" + university.getUnivID() + "\"";

        String update = String.join(",", updates);
        querySql = querySql + update + endQuery;

        JobReference jobId = null;
        Job completedJob = null;
        try {
            jobId = startQuery(bigquery, PROJECT_ID, querySql);
            if (jobId != null) {
                completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);
                if (completedJob != null) {
                    Boolean aBoolean = updatesResult(bigquery, PROJECT_ID, completedJob);
                    return aBoolean;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobId = null;
            completedJob = null;
        }
        return false;
    }

    public static  Map<String, Object> createUniversity(JSONObject body) throws JSONException {

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String UnivID = UtilsManager.generateUniqueID();
        String Name = body.getString("Name");
        String Website = body.getString("Website");
        String AdminID = body.getString("AdminID");

        ArrayList<TableDataInsertAllRequest.Rows> datachunk =
                new ArrayList<TableDataInsertAllRequest.Rows>();
        TableDataInsertAllRequest.Rows row = new TableDataInsertAllRequest.Rows();
        Map<String, Object> data = new HashMap<>();
        data.put("UnivID", UnivID);
        data.put("Name", Name);
        data.put("Website", Website);
        data.put("UnivAdmins", AdminID);
        data.put("Started", UtilsManager.getUTCStandardDateFormat());

        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Action", "University_Started");
        jsonObject.put("Time", UtilsManager.getUTCStandardDateFormat());
        array.put(jsonObject);

        data.put("ActionLogs", array.toString());

        row.setJson(data);
        datachunk.add(row);
        Boolean aBoolean = BQTable_University.insertDataRows(bigquery, datachunk);
        if (aBoolean){
            datachunk =
                    new ArrayList<TableDataInsertAllRequest.Rows>();
            row = new TableDataInsertAllRequest.Rows();
            data = new HashMap<>();
            data.put("PermissionID", UtilsManager.generateUniqueID());
            data.put("UnivID", UnivID);
            data.put("UEM_ID", AdminID);
            data.put("Permissions", "OWNER");
            row.setJson(data);
            datachunk.add(row);
            aBoolean = BQTable_Permissions.insertDataRows(bigquery, datachunk);
            if (aBoolean){
                return data;
            }
        }
        return null;
    }

    public static List<University> getAllUniversities_UnivAdmin_Contains(String UnivAdmin) {

        String PROJECT_ID = "universalevaluationmetrics";

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String querySql = "SELECT\n" +
                "  UnivID,\n" +
                "  Name,\n" +
                "  Photo,\n" +
                "  Started,\n" +
                "  UnivAdmins,\n" +
                "  Students,\n" +
                "  Teachers,\n" +
                "  Courses,\n" +
                "  Website,\n" +
                "  MoreInfo,\n" +
                "  ActionLogs\n" +
                "FROM\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.University`";

        if (UnivAdmin != null) {
            querySql = "SELECT\n" +
                    "  UnivID,\n" +
                    "  Name,\n" +
                    "  Photo,\n" +
                    "  Started,\n" +
                    "  UnivAdmins,\n" +
                    "  Students,\n" +
                    "  Teachers,\n" +
                    "  Courses,\n" +
                    "  Website,\n" +
                    "  MoreInfo,\n" +
                    "  ActionLogs\n" +
                    "FROM\n" +
                    "  `universalevaluationmetrics.universalEvaluationMetrics.University`\n" +
                    "WHERE\n" +
                    "  UnivAdmins LIKE '%" + UnivAdmin + "%'";
        }

        JobReference jobId = null;
        Job completedJob = null;
        ArrayList<University> universities = null;
        try {
            jobId = startQuery(bigquery, PROJECT_ID, querySql);
            if (jobId != null) {
                completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);
                if (completedJob != null) {
                    universities = getUniversities(bigquery, PROJECT_ID, completedJob);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobId = null;
            completedJob = null;
        }

        return universities;
    }

    public static List<University> getAllUniversities_UnivID(String UnivID) {

        String PROJECT_ID = "universalevaluationmetrics";

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String querySql = "SELECT\n" +
                "  UnivID,\n" +
                "  Name,\n" +
                "  Photo,\n" +
                "  Started,\n" +
                "  UnivAdmins,\n" +
                "  Students,\n" +
                "  Teachers,\n" +
                "  Courses,\n" +
                "  Website,\n" +
                "  MoreInfo,\n" +
                "  ActionLogs\n" +
                "FROM\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.University`";

        if (UnivID != null) {
            querySql = "SELECT\n" +
                    "  UnivID,\n" +
                    "  Name,\n" +
                    "  Photo,\n" +
                    "  Started,\n" +
                    "  UnivAdmins,\n" +
                    "  Students,\n" +
                    "  Teachers,\n" +
                    "  Courses,\n" +
                    "  Website,\n" +
                    "  MoreInfo,\n" +
                    "  ActionLogs\n" +
                    "FROM\n" +
                    "  `universalevaluationmetrics.universalEvaluationMetrics.University`\n" +
                    "WHERE\n" +
                    "  UnivID = \"" + UnivID + "\"";
        }

        JobReference jobId = null;
        Job completedJob = null;
        ArrayList<University> universities = null;
        try {
            jobId = startQuery(bigquery, PROJECT_ID, querySql);
            if (jobId != null) {
                completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);
                if (completedJob != null) {
                    universities = getUniversities(bigquery, PROJECT_ID, completedJob);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobId = null;
            completedJob = null;
        }

        return universities;
    }

    public static List<Teacher> getAllTeachers(String UserID) {

        String PROJECT_ID = "universalevaluationmetrics";

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String querySql = "SELECT\n" +
                "  UEM_ID,\n" +
                "  UserID,\n" +
                "  UnivID\n" +
                "FROM\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.Teacher`";

        if (UserID != null) {
            querySql = "SELECT\n" +
                    "  UEM_ID,\n" +
                    "  UserID,\n" +
                    "  UnivID\n" +
                    "FROM\n" +
                    "  `universalevaluationmetrics.universalEvaluationMetrics.Teacher`\n" +
                    "WHERE\n" +
                    "  UserID = '" + UserID + "'";
        }

        JobReference jobId = null;
        Job completedJob = null;
        ArrayList<Teacher> univAdmins = null;
        try {
            jobId = startQuery(bigquery, PROJECT_ID, querySql);
            if (jobId != null) {
                completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);
                if (completedJob != null) {
                    univAdmins = getTeachers(bigquery, PROJECT_ID, completedJob);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobId = null;
            completedJob = null;
        }
        return univAdmins;
    }

    public static List<Student> getAllStudents(String UserID) {

        String PROJECT_ID = "universalevaluationmetrics";

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String querySql = "SELECT\n" +
                "  UEM_ID,\n" +
                "  UserID,\n" +
                "  UnivID\n" +
                "FROM\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.Student`";

        if (UserID != null) {
            querySql = "SELECT\n" +
                    "  UEM_ID,\n" +
                    "  UserID,\n" +
                    "  UnivID\n" +
                    "FROM\n" +
                    "  `universalevaluationmetrics.universalEvaluationMetrics.Student`\n" +
                    "WHERE\n" +
                    "  UserID = '" + UserID + "'";
        }

        JobReference jobId = null;
        Job completedJob = null;
        ArrayList<Student> univAdmins = null;
        try {
            jobId = startQuery(bigquery, PROJECT_ID, querySql);
            if (jobId != null) {
                completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);
                if (completedJob != null) {
                    univAdmins = getUnivStudents(bigquery, PROJECT_ID, completedJob);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobId = null;
            completedJob = null;
        }
        return univAdmins;
    }

    public static List<UnivAdmin> getAllAdmin(String UserID) {

        String PROJECT_ID = "universalevaluationmetrics";

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String querySql = "SELECT\n" +
                "  UEM_ID,\n" +
                "  UserID,\n" +
                "  UnivID\n" +
                "FROM\n" +
                "  `universalevaluationmetrics.universalEvaluationMetrics.UnivAdmin`";

        if (UserID != null) {
            querySql = "SELECT\n" +
                    "  UEM_ID,\n" +
                    "  UserID,\n" +
                    "  UnivID\n" +
                    "FROM\n" +
                    "  `universalevaluationmetrics.universalEvaluationMetrics.UnivAdmin`\n" +
                    "WHERE\n" +
                    "  UserID = '" + UserID + "'";
        }

        JobReference jobId = null;
        Job completedJob = null;
        ArrayList<UnivAdmin> univAdmins = null;
        try {
            jobId = startQuery(bigquery, PROJECT_ID, querySql);
            if (jobId != null) {
                completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);
                if (completedJob != null) {
                    univAdmins = getUnivAdmins(bigquery, PROJECT_ID, completedJob);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobId = null;
            completedJob = null;
        }
        return univAdmins;
    }

    public static List<User> getAllUsers_Email(String email) {

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
        ArrayList<User> users = new ArrayList<>();
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

    public static List<User> getAllUsers_UserID(String UserID) {

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

        if (UserID != null) {
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
                    "  UserID = '" + UserID + "'";
        }

        JobReference jobId = null;
        Job completedJob = null;
        ArrayList<User> users = new ArrayList<>();
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

    private static ArrayList<UnivAdmin> getUnivAdmins(Bigquery bigquery, String projectId,
                                            Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = bigquery.jobs()
                .getQueryResults(projectId, completedJob.getJobReference().getJobId()).execute();
        List<TableRow> rows = queryResult.getRows();
        ArrayList<UnivAdmin> univAdmins = new ArrayList<UnivAdmin>();
        System.out.print("\nQuery Results:\n------------\n");

        if (rows != null) {
            for (TableRow row : rows) {
                LinkedList<TableCell> rowList = new LinkedList<TableCell>();
                rowList.addAll(row.getF());
                UnivAdmin univAdmin = new UnivAdmin();
                univAdmin.setUEM_ID(rowList.get(0).getV().toString());
                univAdmin.setUserID(rowList.get(1).getV().toString());
                univAdmin.setUnivID(rowList.get(2).getV().toString());
                univAdmins.add(univAdmin);
            }
        }

        return univAdmins;

    }

    private static ArrayList<Student> getUnivStudents(Bigquery bigquery, String projectId,
                                                      Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = bigquery.jobs()
                .getQueryResults(projectId, completedJob.getJobReference().getJobId()).execute();
        List<TableRow> rows = queryResult.getRows();
        ArrayList<Student> students = new ArrayList<Student>();
        System.out.print("\nQuery Results:\n------------\n");

        if (rows != null) {
            for (TableRow row : rows) {
                LinkedList<TableCell> rowList = new LinkedList<TableCell>();
                rowList.addAll(row.getF());
                Student student = new Student();
                student.setUEM_ID(rowList.get(0).getV().toString());
                student.setUserID(rowList.get(1).getV().toString());
                student.setUnivID(rowList.get(2).getV().toString());
                students.add(student);
            }
        }

        return students;

    }

    private static Boolean updatesResult(Bigquery bigquery, String projectId,
                                                  Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = bigquery.jobs()
                .getQueryResults(projectId, completedJob.getJobReference().getJobId()).execute();
        List<TableRow> rows = queryResult.getRows();

        return true;

    }

    private static ArrayList<University> getUniversities(Bigquery bigquery, String projectId,
                                                  Job completedJob) throws IOException {

        GetQueryResultsResponse queryResult = bigquery.jobs()
                .getQueryResults(projectId, completedJob.getJobReference().getJobId()).execute();
        List<TableRow> rows = queryResult.getRows();
        ArrayList<University> universities = new ArrayList<>();
        System.out.print("\nQuery Results:\n------------\n");

        if (rows != null) {
            for (TableRow row : rows) {
                LinkedList<TableCell> rowList = new LinkedList<TableCell>();
                rowList.addAll(row.getF());
                University university = new University();
                university.setUnivID(rowList.get(0).getV().toString());
                university.setName(rowList.get(1).getV().toString());
                university.setPhoto(rowList.get(2).getV().toString());
                university.setStarted(rowList.get(3).getV().toString());
                university.setUnivAdmins(rowList.get(4).getV().toString());
                university.setStudents(rowList.get(5).getV().toString());
                university.setTeachers(rowList.get(6).getV().toString());
                university.setCourses(rowList.get(7).getV().toString());
                university.setWebsite(rowList.get(8).getV().toString());
                university.setMoreInfo(rowList.get(9).getV().toString());
                university.setActionLogs(rowList.get(10).getV().toString());

                universities.add(university);
            }
        }

        return universities;

    }

    private static ArrayList<Teacher> getTeachers(Bigquery bigquery, String projectId,
                                                      Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = bigquery.jobs()
                .getQueryResults(projectId, completedJob.getJobReference().getJobId()).execute();
        List<TableRow> rows = queryResult.getRows();
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        System.out.print("\nQuery Results:\n------------\n");

        if (rows != null) {
            for (TableRow row : rows) {
                LinkedList<TableCell> rowList = new LinkedList<TableCell>();
                rowList.addAll(row.getF());
                Teacher teacher = new Teacher();
                teacher.setUEM_ID(rowList.get(0).getV().toString());
                teacher.setUserID(rowList.get(1).getV().toString());
                teacher.setUnivID(rowList.get(2).getV().toString());
                teachers.add(teacher);
            }
        }

        return teachers;

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

    public static Boolean reCreateAllTable(Bigquery bigquery) {

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

    public static Boolean reCreateWholeStructure(Bigquery bigquery) {

        try {

            if (createDataset(bigquery)) {

                if (reCreateAllTable(bigquery)) {

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