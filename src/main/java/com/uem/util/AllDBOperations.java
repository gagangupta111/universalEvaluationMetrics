package com.uem.util;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.*;
import com.google.api.services.bigquery.model.DatasetList.Datasets;
import com.uem.google.bigquery.main.*;
import com.uem.model.*;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

    /*

    Check this link to understand more about finding teh substrings and regex matches in Mongo DB
    https://docs.mongodb.com/manual/reference/operator/query/regex/#examples

    */

public class AllDBOperations {

    private static final String PROJECT_ID = "universalevaluationmetrics";
    private static final String DATASET_ID = "universalEvaluationMetrics";

    public static String toString_() {
        return "AllBQOperations{PROJECT_ID:" + PROJECT_ID + ", DATASET_ID:" + DATASET_ID + "}";
    }

    static Logger logger = LogUtil.getInstance();

    public static Map<String, Object> updateUser(JSONObject body) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            List<User> users = getAllUsers_UserID(body.getString("UserID"));
            if (users == null || users.size() == 0) {
                data.put("message", Constants.NO_INFO_FOUND);
                return data;
            }
            body.remove("UserID");
            Map<String, JSONObject> map = new HashMap<>();
            map.put(users.get(0).getObjectID(), body);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "UniversalUser");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> createUser(String email) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            String UserID = UtilsManager.generateUniqueID();
            String Email = email;
            String Password = UtilsManager.generateUniqueID();
            String UEM_ID = UtilsManager.generateUniqueID();

            JSONObject body = new JSONObject();
            body.put("UserID", UserID);
            body.put("Email", Email);
            body.put("Password", Password);

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(body, "UniversalUser");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                body = new JSONObject();
                body.put("UserID", UserID);
                body.put("UEM_ID", UEM_ID);
                result = ParseUtil.batchCreateInParseTable(body, "UnivAdmin");
                status = Integer.valueOf(String.valueOf(result.get("status")));
                if (status >= 200 && status < 300) {
                    body = new JSONObject();
                    body.put("UserID", UserID);
                    body.put("Email", Email);
                    body.put("Password", Password);
                    body.put("UEM_ID", UEM_ID);

                    data.put("success", true);
                    data.put("body", body);
                    return data;
                } else {
                    data.put("success", false);
                    data.put("response", result.get("response"));
                    data.put("exception", result.get("exception"));
                    return data;
                }
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Boolean updateUniversity(University university) throws JSONException {

        List<String> updates = new ArrayList<>();
        if (university.getName() != null) {
            updates.add("Name = \"" + university.getName() + "\"");
        }
        if (university.getPhoto() != null) {
            updates.add("Photo = \"" + university.getPhoto() + "\"");
        }
        if (university.getUnivAdmins() != null) {
            updates.add("UnivAdmins = \"" + university.getUnivAdmins() + "\"");
        }
        if (university.getStudents() != null) {
            updates.add("Students = \"" + university.getStudents() + "\"");
        }
        if (university.getTeachers() != null) {
            updates.add("Teachers = \"" + university.getTeachers() + "\"");
        }
        if (university.getCourses() != null) {
            updates.add("Courses = \"" + university.getCourses() + "\"");
        }
        if (university.getWebsite() != null) {
            updates.add("Website = \"" + university.getWebsite() + "\"");
        }
        if (university.getMoreInfo() != null) {
            updates.add("MoreInfo = \"" + university.getMoreInfo() + "\"");
        }
        if (university.getActionLogs() != null) {
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

    public static Map<String, Object> createUniversity(JSONObject university) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {
            String UnivID = UtilsManager.generateUniqueID();
            String Name = university.getString("Name");
            String Website = university.getString("Website");
            String AdminID = university.getString("AdminID");

            university.put("UnivID", UnivID);
            university.remove("AdminID");

            JSONArray array = new JSONArray();
            array.put(AdminID);

            university.put("UnivAdmins", array);
            university.put("Started", UtilsManager.getUTCStandardDateFormat());

            array = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Action", "UNIVERSITY_STARTED");
            jsonObject.put("Time", UtilsManager.getUTCStandardDateFormat());
            array.put(jsonObject);

            university.put("ActionLogs", array);

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(university, "University");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                JSONObject permission = new JSONObject();
                permission.put("PermissionID", UtilsManager.generateUniqueID());
                permission.put("UnivID", UnivID);
                permission.put("UEM_ID", AdminID);

                array = new JSONArray();
                array.put("OWNER");

                permission.put("Permissions", array);
                result = ParseUtil.batchCreateInParseTable(permission, "Permissions");
                status = Integer.valueOf(String.valueOf(result.get("status")));

                for (Iterator<String> iter = university.keys(); iter.hasNext(); ) {
                    String key  = iter.next();
                    permission.put(key, (university.get(key)));
                }

                if (status >= 200 && status < 300) {
                    data.put("success", true);
                    data.put("body", permission);
                    return data;
                } else {
                    data.put("success", false);
                    data.put("response", result.get("response"));
                    data.put("exception", result.get("exception"));
                    data.put("body", permission);
                    return data;
                }
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", university);
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static List<University> getAllUniversities_UnivAdmin_Contains(String UnivAdmin) {

        List<University> universities = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UnivAdmin:{$regex:/" + UnivAdmin + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllUniversity(filter);
        if (documents == null || documents.size() == 0) {
            return universities;
        } else {
            for (Document document : documents) {
                University university = new University();
                university.setUnivID(document.getString("UnivID"));
                university.setName((document.getString("Name")));
                university.setStarted((document.getString("Started")));
                university.setUnivAdmins((document.getString("UnivAdmins")));
                university.setStudents(
                        document.containsKey("Students")
                        ? document.getList("Students", Document.class)
                        : new ArrayList<>());
                university.setTeachers(
                        document.containsKey("Teachers")
                                ? document.getList("Teachers", Document.class)
                                : new ArrayList<>());
                university.setCourses(
                        document.containsKey("Courses")
                                ? document.getList("Courses", Document.class)
                                : new ArrayList<>());
                university.setWebsite((document.getString("Website")));
                university.setLegalInfo(
                        document.containsKey("LegalInfo")
                                ? document.get("LegalInfo", Document.class)
                                : new Document());
                university.setMoreInfo(
                        document.containsKey("MoreInfo")
                                ? document.get("MoreInfo", Document.class)
                                : new Document());

                university.setActionLogs(
                        document.containsKey("ActionLogs")
                                ? document.getList("ActionLogs", Document.class)
                                : new ArrayList<>());

                university.setInfo((document.getString("info")));
                university.setPhoto(
                        document.containsKey("Photo")
                                ? document.get("Photo", Document.class)
                                : new Document());

                university.setObjectID(document.getString("_id"));
                university.set_created_at(document.getDate("_created_at"));
                university.set_updated_at(document.getDate("_updated_at"));
                universities.add(university);
            }
        }
        return universities;
    }

    public static List<University> getAllUniversities_UnivID(String UnivID) {

        List<University> universities = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UnivID:{$regex:/" + UnivID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllUniversity(filter);
        if (documents == null || documents.size() == 0) {
            return universities;
        } else {
            for (Document document : documents) {
                University university = new University();
                university.setUnivID(document.getString("UnivID"));
                university.setName((document.getString("Name")));
                university.setStarted((document.getString("Started")));
                university.setUnivAdmins((document.getString("UnivAdmins")));
                university.setStudents(
                        document.containsKey("Students")
                                ? document.getList("Students", Document.class)
                                : new ArrayList<>());
                university.setTeachers(
                        document.containsKey("Teachers")
                                ? document.getList("Teachers", Document.class)
                                : new ArrayList<>());
                university.setCourses(
                        document.containsKey("Courses")
                                ? document.getList("Courses", Document.class)
                                : new ArrayList<>());
                university.setWebsite((document.getString("Website")));
                university.setLegalInfo(
                        document.containsKey("LegalInfo")
                                ? document.get("LegalInfo", Document.class)
                                : new Document());
                university.setMoreInfo(
                        document.containsKey("MoreInfo")
                                ? document.get("MoreInfo", Document.class)
                                : new Document());

                university.setActionLogs(
                        document.containsKey("ActionLogs")
                                ? document.getList("ActionLogs", Document.class)
                                : new ArrayList<>());

                university.setInfo((document.getString("info")));
                university.setPhoto(
                        document.containsKey("Photo")
                                ? document.get("Photo", Document.class)
                                : new Document());

                university.setObjectID(document.getString("_id"));
                university.set_created_at(document.getDate("_created_at"));
                university.set_updated_at(document.getDate("_updated_at"));
                universities.add(university);
            }
        }
        return universities;
    }

    public static List<Teacher> getAllTeachers(String UserID) {

        List<Teacher> teachers = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UserID:{$regex:/" + UserID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllTeachers(filter);
        if (documents == null || documents.size() == 0) {
            return teachers;
        } else {
            for (Document document : documents) {
                Teacher teacher = new Teacher();
                teacher.setUnivID(document.getString("UnivID"));
                teacher.setUserID(document.getString("UserID"));
                teacher.setUEM_ID(document.getString("UEM_ID"));
                teacher.setInfo((document.getString("info")));
                teacher.setDocuments(document.getList("Documents", Document.class));
                teacher.setObjectID(document.getString("_id"));
                teacher.set_created_at(document.getDate("_created_at"));
                teacher.set_updated_at(document.getDate("_updated_at"));
                teachers.add(teacher);
            }
        }
        return teachers;
    }

    public static List<Student> getAllStudents(String UserID) {

        List<Student> students = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UserID:{$regex:/" + UserID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllStudents(filter);
        if (documents == null || documents.size() == 0) {
            return students;
        } else {
            for (Document document : documents) {
                Student student = new Student();
                student.setUnivID(document.getString("UnivID"));
                student.setUserID(document.getString("UserID"));
                student.setUEM_ID(document.getString("UEM_ID"));
                student.setInfo((document.getString("info")));
                student.setDocuments(document.getList("Documents", Document.class));
                student.setObjectID(document.getString("_id"));
                student.set_created_at(document.getDate("_created_at"));
                student.set_updated_at(document.getDate("_updated_at"));
                students.add(student);
            }
        }
        return students;
    }

    public static List<UnivAdmin> getAllAdmin(String UserID) {

        List<UnivAdmin> univAdmins = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UserID:{$regex:/" + UserID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllUniversityAdmin(filter);
        if (documents == null || documents.size() == 0) {
            return univAdmins;
        } else {
            for (Document document : documents) {
                UnivAdmin univAdmin = new UnivAdmin();
                univAdmin.setUnivID(document.getString("UnivID"));
                univAdmin.setUserID(document.getString("UserID"));
                univAdmin.setUEM_ID(document.getString("UEM_ID"));
                univAdmin.setInfo((document.getString("info")));
                univAdmin.setDocuments(document.getList("Documents", Document.class));
                univAdmin.setObjectID(document.getString("_id"));
                univAdmin.set_created_at(document.getDate("_created_at"));
                univAdmin.set_updated_at(document.getDate("_updated_at"));
                univAdmins.add(univAdmin);
            }
        }
        return univAdmins;
    }

    public static List<User> getAllUsers_Email(String email) {

        List<User> users = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "Email:{$regex:/" + email + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllUniversalUsers(filter);
        if (documents == null || documents.size() == 0) {
            return users;
        } else {
            for (Document document : documents) {
                try {
                    User user = new User();
                    user.setUserID(document.getString("UserID"));
                    user.setDOB(document.getString("DOB"));
                    user.setAddress(document.getString("Address"));

                    user.setPhoto((
                            document.containsKey("Photo")
                                    ? document.get("Photo", Document.class)
                                    : new Document()));
                    user.setMobile(document.getString("Mobile"));

                    user.setName(document.getString("Name"));
                    user.setPassword(document.getString("Password"));
                    user.setEmail(document.getString("Email"));

                    user.setObjectID(document.getString("_id"));
                    user.set_created_at(document.getDate("_created_at"));
                    user.set_updated_at(document.getDate("_updated_at"));

                    users.add(user);
                }catch (Exception e){
                    logger.debug(UtilsManager.exceptionAsString(e));
                }
            }
        }
        return users;
    }

    public static List<User> getAllUsers_UserID(String UserID) {

        List<User> users = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UserID:{$regex:/" + UserID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllUniversalUsers(filter);
        if (documents == null || documents.size() == 0) {
            return users;
        } else {
            for (Document document : documents) {
                try {
                    User user = new User();
                    user.setUserID(document.getString("UserID"));
                    user.setDOB(document.getString("DOB"));
                    user.setAddress(document.getString("Address"));

                    user.setPhoto((
                            document.containsKey("Photo")
                                    ? document.get("Photo", Document.class)
                                    : new Document()));
                    user.setMobile(document.getString("Mobile"));

                    user.setName(document.getString("Name"));
                    user.setPassword(document.getString("Password"));
                    user.setEmail(document.getString("Email"));

                    user.setObjectID(document.getString("_id"));
                    user.set_created_at(document.getDate("_created_at"));
                    user.set_updated_at(document.getDate("_updated_at"));

                    users.add(user);
                }catch (Exception e){
                    logger.debug(UtilsManager.exceptionAsString(e));
                }
            }
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
        return null;
    }

    private static ArrayList<Teacher> getTeachers(Bigquery bigquery, String projectId,
                                                  Job completedJob) throws IOException {
        return null;
    }

    private static ArrayList<User> getUsers(Bigquery bigquery, String projectId,
                                            Job completedJob) throws IOException {
        return null;
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
        BQTable_Universal_User.createTable(bigquery, true, true);
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