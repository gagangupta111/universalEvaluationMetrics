package com.uem.util;

import com.uem.model.*;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

    /*

    Check this link to understand more about finding the substrings and regex matches in Mongo DB
    https://docs.mongodb.com/manual/reference/operator/query/regex/#examples

    */

public class AllDBOperations {

    static Logger logger = LogUtil.getInstance();

    public static Map<String, Object> createUser_2(String email, String password) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            String UserID = UtilsManager.generateUniqueID();
            String Email = email;
            String Password = password;

            JSONObject body = new JSONObject();
            body.put("UserID", UserID);
            body.put("Email", Email);
            body.put("Password", Password);

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(body, "UniversalUser");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {

                data.put("success", true);
                data.put("body", body);
                return data;

            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
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

            JSONObject body = new JSONObject();
            body.put("UserID", UserID);
            body.put("Email", Email);
            body.put("Password", Password);

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(body, "UniversalUser");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {

                data.put("success", true);
                data.put("body", body);
                return data;

            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> createAdmin(String UserID) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            String UEM_ID = UtilsManager.generateUniqueID();

            JSONObject body = new JSONObject();
            body.put("UserID", UserID);
            body.put("UEM_ID", UEM_ID);
            Map<String, Object> result = ParseUtil.batchCreateInParseTable(body, "UnivAdmin");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {

                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }

    }

    public static Map<String, Object> createLogs(Logs log) {

        Map<String, Object> data = new HashMap<>();

        String LogID = UtilsManager.generateUniqueID();
        log.setLogID(LogID);

        data.put("success", false);
        try {

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(UtilsManager.logsToJson(log), "Logs");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", log);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", log);
                return data;
            }
        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            data.put("body", log);
            return data;
        }
    }

    public static Map<String, Object> createConnection(JSONObject message) {

        Map<String, Object> data = new HashMap<>();

        String ConnectionID = UtilsManager.generateUniqueID();
        try {
            message.put("ConnectionID", ConnectionID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        data.put("success", false);
        try {

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(message, "Connections");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", message);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", message);
                return data;
            }
        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            data.put("body", message);
            return data;
        }
    }

    public static Map<String, Object> createNotification(JSONObject message) {

        Map<String, Object> data = new HashMap<>();
        if (!message.has("read")){
            try {
                message.put("read", "false");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String NotificationID = UtilsManager.generateUniqueID();
        try {
            message.put("NotificationID", NotificationID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        data.put("success", false);
        try {

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(message, "Notifications");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", message);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", message);
                return data;
            }
        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            data.put("body", message);
            return data;
        }
    }

    public static Map<String, Object> createMessage(JSONObject message) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(message, "Messages");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", message);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", message);
                return data;
            }
        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            data.put("body", message);
            return data;
        }
    }

    public static Map<String, Object> createEvent(JSONObject event) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {
            String EventID = UtilsManager.generateUniqueID();

            event.put("EventID", EventID);

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(event, "Events");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", event);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", event);
                return data;
            }
        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            data.put("body", event);
            return data;
        }
    }

    public static Map<String, Object> createPost(JSONObject post) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {
            String PostID = UtilsManager.generateUniqueID();

            post.put("PostID", PostID);
            post.put("likes", "0");
            post.put("shares", "0");

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(post, "Posts");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", post);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", post);
                return data;
            }
        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            data.put("body", post);
            return data;
        }
    }

    public static Map<String, Object> createTeacher(String UserID) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            String UEM_ID = UtilsManager.generateUniqueID();

            JSONObject body = new JSONObject();
            body.put("UserID", UserID);
            body.put("UEM_ID", UEM_ID);
            Map<String, Object> result = ParseUtil.batchCreateInParseTable(body, "Teacher");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {

                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }

    }

    public static Map<String, Object> createStudent(String UserID) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            String UEM_ID = UtilsManager.generateUniqueID();

            JSONObject body = new JSONObject();
            body.put("UserID", UserID);
            body.put("UEM_ID", UEM_ID);
            Map<String, Object> result = ParseUtil.batchCreateInParseTable(body, "Student");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {

                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }

    }

    public static Map<String, Object> createCourseAdmin(String UserID) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            String UEM_ID = UtilsManager.generateUniqueID();

            JSONObject body = new JSONObject();
            body.put("UserID", UserID);
            body.put("UEM_ID", UEM_ID);
            Map<String, Object> result = ParseUtil.batchCreateInParseTable(body, "CourseAdmin");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {

                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }

    }

    public static Map<String, Object> createBatch(JSONObject batch) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {
            String BatchID = UtilsManager.generateUniqueID();
            String Duration = batch.getString("Duration");
            String SpanOver = batch.getString("SpanOver");
            String Starting = batch.getString("Starting");
            String Completion = batch.getString("Completion");
            JSONObject Calender = batch.getJSONObject("Calendar");
            JSONObject Billing = batch.getJSONObject("Billing");
            String CourseID = batch.getString("CourseID");
            String AdminID = batch.getString("CourseID");

            batch.put("BatchID", BatchID);

            JSONArray array = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Action", "BATCH_STARTED");
            jsonObject.put("STARTED_BY", AdminID);
            jsonObject.put("Time", UtilsManager.getUTCStandardDateFormat());
            array.put(jsonObject);

            batch.put("ActionLogs", array);

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(batch, "Batch");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", batch);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", batch);
                return data;
            }
        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            data.put("body", batch);
            return data;
        }
    }

    public static Map<String, Object> updateBatch(Batch batch, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            if (append) {

                Batch toBeAppended = UtilsManager.jsonToBatch(body);
                if (toBeAppended.getActionLogs() != null && toBeAppended.getActionLogs().size() > 0
                        && batch.getActionLogs() != null && batch.getActionLogs().size() > 0) {
                    toBeAppended.getActionLogs().addAll(batch.getActionLogs());
                }

                if (toBeAppended.getStudents() != null && toBeAppended.getStudents().size() > 0
                        && batch.getStudents() != null && batch.getStudents().size() > 0) {
                    toBeAppended.setStudents(UtilsManager.mergeDocuments(batch.getStudents(), toBeAppended.getStudents(), "id"));
                }

                if (toBeAppended.getFellowTutors() != null && toBeAppended.getFellowTutors().size() > 0
                        && batch.getFellowTutors() != null && batch.getFellowTutors().size() > 0) {
                    toBeAppended.setFellowTutors(UtilsManager.mergeDocuments(batch.getFellowTutors(), toBeAppended.getFellowTutors(), "id"));
                }

                if (toBeAppended.getLeadTutors() != null && toBeAppended.getLeadTutors().size() > 0
                        && batch.getLeadTutors() != null && batch.getLeadTutors().size() > 0) {
                    toBeAppended.setLeadTutors(UtilsManager.mergeDocuments(batch.getLeadTutors(), toBeAppended.getLeadTutors(), "id"));
                }

                body = UtilsManager.batchToJson(toBeAppended);

            } else {
                body = UtilsManager.batchToJson(UtilsManager.jsonToBatch(body));
            }

            // update ActionLogs
            JSONObject jsonObject = UtilsManager.batchToJson(batch);
            JSONArray array = jsonObject.has("ActionLogs") ? jsonObject.getJSONArray("ActionLogs") : new JSONArray();
            JSONObject actionLog = new JSONObject();
            actionLog.put("Action", "BATCH_UPDATED");
            actionLog.put("Time", UtilsManager.getUTCStandardDateFormat());
            actionLog.put("Value", body.toString());
            array.put(actionLog);
            body.put("ActionLogs", array);

            Map<String, JSONObject> map = new HashMap<>();
            map.put(batch.getObjectID(), body);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "Batch");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> deleteFromBatch(Batch batch, JSONObject tobeDeleted) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            JSONObject tobeDeletedBody = new JSONObject();
            Batch tobeDeletedBatch = new Batch();

            if (tobeDeleted.has("LeadTutors") && batch.getLeadTutors() != null && batch.getLeadTutors().size() > 0){

                String LeadTutors = tobeDeleted.getString("LeadTutors");
                String[] deleteList = LeadTutors.split(",");

                List<Document> list = (UtilsManager.deleteDocuments(batch.getLeadTutors(), deleteList, "id"));

                if (list != null && list.size() > 0){
                    tobeDeletedBatch.setLeadTutors(list);
                }else {
                    tobeDeletedBody.put("LeadTutors", new JSONArray());
                }
            }

            if (tobeDeleted.has("FellowTutors") && batch.getFellowTutors() != null && batch.getFellowTutors().size() > 0){

                String LeadTutors = tobeDeleted.getString("FellowTutors");
                String[] deleteList = LeadTutors.split(",");

                List<Document> list = (UtilsManager.deleteDocuments(batch.getFellowTutors(), deleteList, "id"));
                if (list != null && list.size() > 0){
                    tobeDeletedBatch.setFellowTutors(list);
                }else {
                    tobeDeletedBody.put("FellowTutors", new JSONArray());
                }
            }

            if (tobeDeleted.has("Students") && batch.getStudents() != null && batch.getStudents().size() > 0){

                String LeadTutors = tobeDeleted.getString("Students");
                String[] deleteList = LeadTutors.split(",");

                List<Document> list = (UtilsManager.deleteDocuments(batch.getStudents(), deleteList, "id"));
                if (list != null && list.size() > 0){
                    tobeDeletedBatch.setStudents(list);
                }else {
                    tobeDeletedBody.put("Students", new JSONArray());
                }
            }

            if (tobeDeleted.has("info") && batch.getInfo() != null && batch.getInfo().size() > 0){

                String LeadTutors = tobeDeleted.getString("info");
                String[] deleteList = LeadTutors.split(",");

                Document document = (UtilsManager.deleteKeysFromDocument(batch.getInfo(), deleteList));
                if (document != null && document.size() > 0){
                    tobeDeletedBatch.setInfo(document);
                }else {
                    tobeDeletedBody.put("info", new JSONObject());
                }
            }

            if (tobeDeleted.has("Billing") && batch.getBilling() != null && batch.getBilling().size() > 0){

                String LeadTutors = tobeDeleted.getString("Billing");
                String[] deleteList = LeadTutors.split(",");

                Document document = (UtilsManager.deleteKeysFromDocument(batch.getBilling(), deleteList));
                if (document != null && document.size() > 0){
                    tobeDeletedBatch.setBilling(document);
                }else {
                    tobeDeletedBody.put("Billing", new JSONObject());
                }

            }

            if (tobeDeleted.has("Calendar") && batch.getCalendar() != null && batch.getCalendar().size() > 0){

                String LeadTutors = tobeDeleted.getString("Calendar");
                String[] deleteList = LeadTutors.split(",");

                Document document = (UtilsManager.deleteKeysFromDocument(batch.getCalendar(), deleteList));
                if (document != null && document.size() > 0){
                    tobeDeletedBatch.setCalendar(document);
                }else {
                    tobeDeletedBody.put("Calendar", new JSONObject());
                }
            }

            if (tobeDeleted.has("Status") && batch.getStatus() != null && batch.getStatus().size() > 0){

                String LeadTutors = tobeDeleted.getString("Status");
                String[] deleteList = LeadTutors.split(",");

                Document document = (UtilsManager.deleteKeysFromDocument(batch.getStatus(), deleteList));
                if (document != null && document.size() > 0){
                    tobeDeletedBatch.setStatus(document);
                }else {
                    tobeDeletedBody.put("Status", new JSONObject());
                }
            }

            JSONObject merge = UtilsManager.batchToJson(tobeDeletedBatch);
            for (Iterator it = merge.keys(); it.hasNext(); ) {
                String key = String.valueOf(it.next());
                tobeDeletedBody.put(key, merge.get(key));
            }

            // update ActionLogs
            JSONObject jsonObject = UtilsManager.batchToJson(batch);
            JSONArray array = jsonObject.has("ActionLogs") ? jsonObject.getJSONArray("ActionLogs") : new JSONArray();
            JSONObject actionLog = new JSONObject();
            actionLog.put("Action", "DELETED_FROM_BATCH");
            actionLog.put("Time", UtilsManager.getUTCStandardDateFormat());
            actionLog.put("Value", tobeDeleted.toString());
            array.put(actionLog);
            tobeDeletedBody.put("ActionLogs", array);

            Map<String, JSONObject> map = new HashMap<>();
            map.put(batch.getObjectID(), tobeDeletedBody);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "Batch");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", tobeDeletedBody);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", tobeDeletedBody);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
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
                    String key = iter.next();
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

    public static Map<String, Object> createCourse(JSONObject course) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {
            String CourseID = UtilsManager.generateUniqueID();
            String Name = course.getString("Name");
            String CourseAdmin = course.getString("CourseAdmin");

            course.put("CourseID", CourseID);

            Map<String, Object> result = ParseUtil.batchCreateInParseTable(course, "Course");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));
            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", course);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", course);
                return data;
            }

        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> updateUser_Email(JSONObject body) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            List<User> users = getAllUsers_Email(body.getString("Email"));
            if (users == null || users.size() == 0) {
                data.put("message", Constants.NO_INFO_FOUND);
                return data;
            }
            body.remove("Email");
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

    public static Map<String, Object> updateUniversity(University university, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            if (append) {

                University toBeAppended = UtilsManager.jsonToUniversity(body);
                if (toBeAppended.getActionLogs() != null && toBeAppended.getActionLogs().size() > 0
                        && university.getActionLogs() != null && university.getActionLogs().size() > 0) {
                    toBeAppended.getActionLogs().addAll(university.getActionLogs());
                }
                if (toBeAppended.getCourses() != null && toBeAppended.getCourses().size() > 0
                        && university.getCourses() != null && university.getCourses().size() > 0) {
                    toBeAppended.setCourses(UtilsManager.mergeDocuments(university.getCourses(), toBeAppended.getCourses(), "id"));
                }
                if (toBeAppended.getStudents() != null && toBeAppended.getStudents().size() > 0
                        && university.getStudents() != null && university.getStudents().size() > 0) {
                    toBeAppended.setStudents(UtilsManager.mergeDocuments(university.getStudents(), toBeAppended.getStudents(), "id"));
                }
                if (toBeAppended.getTeachers() != null && toBeAppended.getTeachers().size() > 0
                        && university.getTeachers() != null && university.getTeachers().size() > 0) {
                    toBeAppended.setTeachers(UtilsManager.mergeDocuments(university.getTeachers(), toBeAppended.getTeachers(), "id"));
                }
                if (toBeAppended.getUnivAdmins() != null && toBeAppended.getUnivAdmins().size() > 0
                        && university.getUnivAdmins() != null && university.getUnivAdmins().size() > 0) {
                    toBeAppended.getUnivAdmins().addAll(university.getUnivAdmins());
                }

                body = UtilsManager.universityToJson(toBeAppended);

            } else {
                body = UtilsManager.universityToJson(UtilsManager.jsonToUniversity(body));
            }

            // update ActionLogs
            JSONObject jsonObject = UtilsManager.universityToJson(university);
            JSONArray array = jsonObject.has("ActionLogs") ? jsonObject.getJSONArray("ActionLogs") : new JSONArray();
            JSONObject actionLog = new JSONObject();
            actionLog.put("Action", "UNIVERSITY_UPDATED");
            actionLog.put("Time", UtilsManager.getUTCStandardDateFormat());
            actionLog.put("Value", body.toString());
            array.put(actionLog);
            body.put("ActionLogs", array);

            Map<String, JSONObject> map = new HashMap<>();
            map.put(university.getObjectID(), body);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "University");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> updateConnections(Connection connection, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            if (body.has("status")){
                connection.setStatus(body.getString("status"));
            }

            JSONObject bodyUpdate = new JSONObject();
            bodyUpdate.put("status", connection.getStatus());

            Map<String, JSONObject> map = new HashMap<>();
            map.put(connection.getObjectID(), bodyUpdate);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "Connections");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> updateNotifications(Notification notification, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            JSONObject bodyUpdate = new JSONObject();
            if (body.has("text") && !body.getString("text").equalsIgnoreCase("none")){
                bodyUpdate.put("text", body.getString("text"));
            }

            if (body.has("read") && !body.getString("read").equalsIgnoreCase("none")){
                bodyUpdate.put("read", body.getString("read"));
            }

            if (bodyUpdate.length() == 0){
                data.put("success", false);
                data.put("response", "NOTHING_TO_UPDATE");
                data.put("body", body);
                return data;
            }

            Map<String, JSONObject> map = new HashMap<>();
            map.put(notification.getObjectID(), bodyUpdate);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "Notifications");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> updateMessage(Message message, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            if (body.has("read")){
                message.setRead(body.getString("read"));
            }

            JSONObject bodyUpdate = new JSONObject();
            bodyUpdate.put("read", message.getRead());

            Map<String, JSONObject> map = new HashMap<>();
            map.put(message.getObjectID(), bodyUpdate);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "Messages");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> updatePost(Post post, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            JSONObject bodyUpdate = new JSONObject();

            if (body.has("text") && !body.getString("text").equalsIgnoreCase("none")){
                bodyUpdate.put("text", body.getString("text"));
            }

            if (body.has("likes") && post.getLikes() != null && !post.getLikes().equalsIgnoreCase("") && body.getString("likes").equalsIgnoreCase("+1")){
                bodyUpdate.put("likes", "" + (Integer.valueOf(post.getLikes()) + 1));
            }else if (body.has("likes") && body.getString("likes").equalsIgnoreCase("+1")){
                bodyUpdate.put("likes", "" + 1);
            }

            if (body.has("shares") && post.getShares() != null && !post.getShares().equalsIgnoreCase("") && body.getString("shares").equalsIgnoreCase("+1")){
                bodyUpdate.put("shares", "" + (Integer.valueOf(post.getShares()) + 1));
            }else if (body.has("shares") && body.getString("shares").equalsIgnoreCase("+1")){
                bodyUpdate.put("shares", "" + 1);
            }

            Map<String, JSONObject> map = new HashMap<>();
            map.put(post.getObjectID(), bodyUpdate);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "Posts");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> updateAdmin(UnivAdmin univAdmin, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            if (append) {

                UnivAdmin toBeAppended = UtilsManager.jsonToAdmin(body);
                if (toBeAppended.getDocuments() != null && toBeAppended.getDocuments().size() > 0
                        && univAdmin.getDocuments() != null && univAdmin.getDocuments().size() > 0) {
                    toBeAppended.getDocuments().addAll(univAdmin.getDocuments());
                }

                body = UtilsManager.adminToJson(toBeAppended);

            } else {
                body = UtilsManager.adminToJson(UtilsManager.jsonToAdmin(body));
            }

            Map<String, JSONObject> map = new HashMap<>();
            map.put(univAdmin.getObjectID(), body);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "UnivAdmin");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> updateStudent(Student student, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            if (append) {

                Student toBeAppended = UtilsManager.jsonToStudent(body);
                if (toBeAppended.getDocuments() != null && toBeAppended.getDocuments().size() > 0
                        && student.getDocuments() != null && student.getDocuments().size() > 0) {
                    toBeAppended.getDocuments().addAll(student.getDocuments());
                }

                if (toBeAppended.getBatches() != null && toBeAppended.getBatches().size() > 0
                        && student.getBatches() != null && student.getBatches().size() > 0) {
                    toBeAppended.setBatches(UtilsManager.mergeDocuments(student.getBatches(), toBeAppended.getBatches(), "id"));
                }

                body = UtilsManager.studentToJson(toBeAppended);

            } else {
                body = UtilsManager.studentToJson(UtilsManager.jsonToStudent(body));
            }

            Map<String, JSONObject> map = new HashMap<>();
            map.put(student.getObjectID(), body);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "Student");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
                return data;
            }


        } catch (Exception e) {
            data.put("exception", UtilsManager.exceptionAsString(e));
            return data;
        }
    }

    public static Map<String, Object> updateTeacher(Teacher teacher, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            if (append) {

                Teacher toBeAppended = UtilsManager.jsonToTeacher(body);
                if (toBeAppended.getDocuments() != null && toBeAppended.getDocuments().size() > 0
                        && teacher.getDocuments() != null && teacher.getDocuments().size() > 0) {
                    toBeAppended.getDocuments().addAll(teacher.getDocuments());
                }

                if (toBeAppended.getBatches() != null && toBeAppended.getBatches().size() > 0
                        && teacher.getBatches() != null && teacher.getBatches().size() > 0) {
                    toBeAppended.setBatches(UtilsManager.mergeDocuments(teacher.getBatches(), toBeAppended.getBatches(), "id"));
                }

                body = UtilsManager.teacherToJson(toBeAppended);

            } else {
                body = UtilsManager.teacherToJson(UtilsManager.jsonToTeacher(body));
            }

            Map<String, JSONObject> map = new HashMap<>();
            map.put(teacher.getObjectID(), body);

            Map<String, Object> result = ParseUtil.batchUpdateInParseTable(map, "Teacher");
            Integer status = Integer.valueOf(String.valueOf(result.get("status")));

            if (status >= 200 && status < 300) {
                data.put("success", true);
                data.put("body", body);
                return data;
            } else {
                data.put("success", false);
                data.put("response", result.get("response"));
                data.put("exception", result.get("exception"));
                data.put("body", body);
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
                        "UnivAdmins:{$regex:/" + UnivAdmin + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllUniversity(filter);
        if (documents == null || documents.size() == 0) {
            return universities;
        } else {
            for (Document document : documents) {
                University university = new University();
                university.setUnivID(document.containsKey("UnivID") ? document.getString("UnivID") : null);

                university.setName(document.containsKey("Name") ? document.getString("Name") : null);
                university.setStarted(document.containsKey("Started") ? document.getString("Started") : null);
                university.setUnivAdmins(document.containsKey("UnivID") ? document.getList("UnivAdmins", String.class) : new ArrayList<>());

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
                university.setUnivID(document.containsKey("UnivID") ? document.getString("UnivID") : null);

                university.setName(document.containsKey("Name") ? document.getString("Name") : null);
                university.setStarted(document.containsKey("Started") ? document.getString("Started") : null);
                university.setUnivAdmins(document.containsKey("UnivID") ? document.getList("UnivAdmins", String.class) : new ArrayList<>());

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

    public static List<Teacher> getAllTeachers_UserID(String UserID) {

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
                Teacher univAdmin = new Teacher();
                univAdmin.setUnivID(document.containsKey("UnivID") ? document.getString("UnivID") : null);

                univAdmin.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                univAdmin.setUEM_ID(document.containsKey("UEM_ID") ? document.getString("UEM_ID") : null);
                univAdmin.setInfo(document.containsKey("info") ? document.getString("info") : null);
                univAdmin.setDocuments(document.containsKey("Documents") ? document.getList("Documents", Document.class) : null);
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                teachers.add(univAdmin);
            }
        }
        return teachers;
    }

    public static List<Student> getAllStudents_UserID(String UserID) {

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
                Student univAdmin = new Student();
                univAdmin.setUnivID(document.containsKey("UnivID") ? document.getString("UnivID") : null);

                univAdmin.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                univAdmin.setUEM_ID(document.containsKey("UEM_ID") ? document.getString("UEM_ID") : null);
                univAdmin.setInfo(document.containsKey("info") ? document.getString("info") : null);
                univAdmin.setDocuments(document.containsKey("Documents") ? document.getList("Documents", Document.class) : null);
                univAdmin.setBatches(document.containsKey("Batches") ? document.getList("Batches", Document.class) : null);
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                students.add(univAdmin);
            }
        }
        return students;
    }

    public static List<CourseAdmin> getAllCourseAdmins_UserID(String UserID) {

        List<CourseAdmin> students = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UserID:{$regex:/" + UserID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllCourseAdmin(filter);
        if (documents == null || documents.size() == 0) {
            return students;
        } else {
            for (Document document : documents) {
                CourseAdmin univAdmin = new CourseAdmin();
                univAdmin.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                univAdmin.setUEM_ID(document.containsKey("UEM_ID") ? document.getString("UEM_ID") : null);
                univAdmin.setInfo(document.containsKey("info") ? document.getString("info") : null);
                univAdmin.setDocuments(document.containsKey("Documents") ? document.getList("Documents", Document.class) : null);
                univAdmin.setCourses(document.containsKey("Courses") ? document.getList("Courses", Document.class) : null);
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                students.add(univAdmin);
            }
        }
        return students;
    }

    public static List<Course> getAllCoursesInUEMBID(String CourseID) {

        List<Course> courses = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "CourseID:{$regex:/" + CourseID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllCourse(filter);
        if (documents == null || documents.size() == 0) {
            return courses;
        } else {
            for (Document document : documents) {
                Course course = new Course();
                course.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                course.setName(document.containsKey("Name") ? document.getString("Name") : null);
                course.setLevel(document.containsKey("Level") ? document.getString("Level") : null);
                course.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                course.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                course.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                course.setExpiring(document.containsKey("Expiring") ? document.getString("Expiring") : null);
                course.setInfo(document.containsKey("info") ? document.getString("info") : null);
                course.setCourseAdmin(document.containsKey("CourseAdmin") ? document.getString("CourseAdmin") : null);

                course.setBatches(document.containsKey("Batches") ? document.getList("Batches", Document.class) : null);
                course.setUniversities(document.containsKey("Universities") ? document.getList("Universities", Document.class) : null);

                course.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                course.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                course.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                course.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                courses.add(course);
            }
        }
        return courses;
    }

    public static List<Course> getAllCoursesInUEM_CourseAdmin(String CourseAdmin) {

        List<Course> courses = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "CourseAdmin:{$regex:/" + CourseAdmin + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllCourse(filter);
        if (documents == null || documents.size() == 0) {
            return courses;
        } else {
            for (Document document : documents) {
                Course course = new Course();
                course.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                course.setName(document.containsKey("Name") ? document.getString("Name") : null);
                course.setLevel(document.containsKey("Level") ? document.getString("Level") : null);
                course.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                course.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                course.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                course.setExpiring(document.containsKey("Expiring") ? document.getString("Expiring") : null);
                course.setInfo(document.containsKey("info") ? document.getString("info") : null);
                course.setCourseAdmin(document.containsKey("CourseAdmin") ? document.getString("CourseAdmin") : null);

                course.setBatches(document.containsKey("Batches") ? document.getList("Batches", Document.class) : null);
                course.setUniversities(document.containsKey("Universities") ? document.getList("Universities", Document.class) : null);

                course.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                course.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                course.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                course.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                courses.add(course);
            }
        }
        return courses;
    }

    public static List<Course> getAllCoursesInUEM(String name) {

        List<Course> courses = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "Name:{$regex:/" + name + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllCourse(filter);
        if (documents == null || documents.size() == 0) {
            return courses;
        } else {
            for (Document document : documents) {
                Course course = new Course();
                course.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                course.setName(document.containsKey("Name") ? document.getString("Name") : null);
                course.setLevel(document.containsKey("Level") ? document.getString("Level") : null);
                course.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                course.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                course.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                course.setExpiring(document.containsKey("Expiring") ? document.getString("Expiring") : null);
                course.setInfo(document.containsKey("info") ? document.getString("info") : null);
                course.setCourseAdmin(document.containsKey("CourseAdmin") ? document.getString("CourseAdmin") : null);

                course.setBatches(document.containsKey("Batches") ? document.getList("Batches", Document.class) : null);
                course.setUniversities(document.containsKey("Universities") ? document.getList("Universities", Document.class) : null);

                course.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                course.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                course.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                course.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                courses.add(course);
            }
        }
        return courses;
    }

    public static List<Notification> getAllNotificationsInUEM(JSONObject body) {

        List<Notification> notifications = new ArrayList<>();

        String UserID = "";
        try {
            UserID = body.has("UserID") ? String.valueOf(body.get("UserID")) : "";
        }catch (Exception e){
            UserID = "";
        }

        String read = "";
        try {
            read = body.has("read") ? String.valueOf(body.get("read")) : "";
        }catch (Exception e){
            read = "";
        }

        String text = "";
        try {
            text = body.has("text") ? String.valueOf(body.get("text")) : "";
        }catch (Exception e){
            text = "";
        }

        String NotificationID = "";
        try {
            NotificationID = body.has("NotificationID") ? String.valueOf(body.get("NotificationID")) : "";
        }catch (Exception e){
            NotificationID = "";
        }

        List<String> filterString = new ArrayList<>();

        if (UserID.length() > 0 && !UserID.equalsIgnoreCase("none")){

            filterString.add("UserID:{$regex:/" + UserID + "/}");

        }

        if (read.length() > 0 && (read.equalsIgnoreCase("true") || read.equalsIgnoreCase("false"))){

            filterString.add("read:{$regex:/" + read + "/}");

        }

        if (text.length() > 0){

            filterString.add("text:{$regex:/" + text + "/}");

        }

        if (NotificationID.length() > 0 && !NotificationID.equalsIgnoreCase("none")){

            filterString.add("NotificationID:{$regex:/" + NotificationID + "/}");

        }

        String filterStringFinal = "";
        if (filterString.size() > 0){
            filterStringFinal = "{ " + String.join(",", filterString) + " }";
        }else {
            filterStringFinal = "{ " + " }";
        }

        BsonDocument filter = BsonDocument.parse(filterStringFinal);

        List<Document> documents = MongoDBUtil.getAllNotifications(filter);
        if (documents == null || documents.size() == 0) {
            return notifications;
        } else {
            for (Document document : documents) {
                Notification notification = new Notification();

                notification.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                notification.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                notification.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                notification.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                notification.setText(document.containsKey("text") ? document.getString("text") : null);
                notification.setRead(document.containsKey("read") ? document.getString("read") : null);
                notification.setNotificationID(document.containsKey("NotificationID") ? document.getString("NotificationID") : null);

                notifications.add(notification);
            }
        }
        Collections.sort(notifications, new NotificationComparatorByUpdatedAt_desc());
        return notifications;
    }

    public static List<Connection> searchAllConnectionsInUEM(JSONObject body) {

        List<Connection> connections = new ArrayList<>();

        String User = "";
        try {
            User = body.has("User") ? String.valueOf(body.get("User")) : "";
        }catch (Exception e){
            User = "";
        }

        String status = "";
        try {
            status = body.has("status") ? String.valueOf(body.get("status")) : "";
        }catch (Exception e){
            status = "";
        }

        List<String> filterString = new ArrayList<>();

        if (User.length() > 0){

            filterString.add("From:{$regex:/" + User + "/}");

        }

        if (status.length() > 0 && !status.equalsIgnoreCase("none")){

            filterString.add("status:{$regex:/" + status + "/}");

        }

        String filterStringFinal = "";
        if (filterString.size() > 0){
            filterStringFinal = "{ " + String.join(",", filterString) + " }";
        }else {
            filterStringFinal = "{ " + " }";
        }

        BsonDocument filter = BsonDocument.parse(filterStringFinal);

        List<Document> documents = MongoDBUtil.getAllConnections(filter);

        filterString = new ArrayList<>();

        if (User.length() > 0){

            filterString.add("To:{$regex:/" + User + "/}");

        }

        if (status.length() > 0 && !status.equalsIgnoreCase("none")){

            filterString.add("status:{$regex:/" + status + "/}");

        }

        filterStringFinal = "";
        if (filterString.size() > 0){
            filterStringFinal = "{ " + String.join(",", filterString) + " }";
        }else {
            filterStringFinal = "{ " + " }";
        }

        filter = BsonDocument.parse(filterStringFinal);

        documents.addAll(MongoDBUtil.getAllConnections(filter));

        if (documents == null || documents.size() == 0) {
            return connections;
        } else {
            for (Document document : documents) {
                Connection connection = new Connection();

                connection.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                connection.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                connection.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);


                connection.setConnectionID(document.containsKey("ConnectionID") ? document.getString("ConnectionID") : null);
                connection.setFrom(document.containsKey("From") ? document.getString("From") : null);
                connection.setTo(document.containsKey("To") ? document.getString("To") : null);
                connection.setStatus(document.containsKey("status") ? document.getString("status") : null);

                connections.add(connection);
            }
        }
        Collections.sort(connections, new ConnectionComparatorByUpdatedAt_Desc());
        return connections;
    }

    public static List<Connection> getAllConnectionsInUEM(JSONObject body) {

        List<Connection> connections = new ArrayList<>();

        String From = "";
        try {
            From = body.has("From") ? String.valueOf(body.get("From")) : "";
        }catch (Exception e){
            From = "";
        }

        String To = "";
        try {
            To = body.has("To") ? String.valueOf(body.get("To")) : "";
        }catch (Exception e){
            To = "";
        }

        String status = "";
        try {
            status = body.has("status") ? String.valueOf(body.get("status")) : "";
        }catch (Exception e){
            status = "";
        }

        String ConnectionID = "";
        try {
            ConnectionID = body.has("ConnectionID") ? String.valueOf(body.get("ConnectionID")) : "";
        }catch (Exception e){
            ConnectionID = "";
        }

        List<String> filterString = new ArrayList<>();

        if (From.length() > 0 && !From.equalsIgnoreCase("none")){

            filterString.add("From:{$regex:/" + From + "/}");

        }

        if (To.length() > 0 && !To.equalsIgnoreCase("none")){

            filterString.add("To:{$regex:/" + To + "/}");

        }

        if (status.length() > 0 && !status.equalsIgnoreCase("none")){

            filterString.add("status:{$regex:/" + status + "/}");

        }

        if (ConnectionID.length() > 0 && !ConnectionID.equalsIgnoreCase("none")){

            filterString.add("ConnectionID:{$regex:/" + ConnectionID + "/}");

        }

        String filterStringFinal = "";
        if (filterString.size() > 0){
            filterStringFinal = "{ " + String.join(",", filterString) + " }";
        }else {
            filterStringFinal = "{ " + " }";
        }

        BsonDocument filter = BsonDocument.parse(filterStringFinal);

        List<Document> documents = MongoDBUtil.getAllConnections(filter);
        if (documents == null || documents.size() == 0) {
            return connections;
        } else {
            for (Document document : documents) {
                Connection connection = new Connection();

                connection.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                connection.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                connection.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);


                connection.setConnectionID(document.containsKey("ConnectionID") ? document.getString("ConnectionID") : null);
                connection.setFrom(document.containsKey("From") ? document.getString("From") : null);
                connection.setTo(document.containsKey("To") ? document.getString("To") : null);
                connection.setStatus(document.containsKey("status") ? document.getString("status") : null);

                connections.add(connection);
            }
        }
        Collections.sort(connections, new ConnectionComparatorByUpdatedAt_Desc());
        return connections;
    }

    public static List<Post> getAllPostsInUEM(JSONObject body) {

        List<Post> posts = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "}");

        String UserID = "";
        try {
            UserID = body.has("UserID") ? String.valueOf(body.get("UserID")) : "";
        }catch (Exception e){
            UserID = "";
        }

        String PostID = "";
        try {
            PostID = body.has("PostID") ? String.valueOf(body.get("PostID")) : "";
        }catch (Exception e){
            UserID = "";
        }

        if (UserID != null && !UserID.equalsIgnoreCase("")){
            filter = BsonDocument
                    .parse("{ " +
                            "UserID:{$regex:/" + UserID + "/}" +
                            "}");
        }

        if (PostID != null && !PostID.equalsIgnoreCase("")){
            filter = BsonDocument
                    .parse("{ " +
                            "PostID:{$regex:/" + PostID + "/}" +
                            "}");
        }

        List<Document> documents = MongoDBUtil.getAllPosts(filter);
        if (documents == null || documents.size() == 0) {
            return posts;
        } else {
            for (Document document : documents) {
                Post post = new Post();

                post.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                post.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                post.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);


                post.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                post.setText(document.containsKey("text") ? document.getString("text") : null);

                post.setLikes(document.containsKey("likes") ? document.getString("likes") : null);
                post.setShares(document.containsKey("shares") ? document.getString("shares") : null);
                post.setPostID(document.containsKey("PostID") ? document.getString("PostID") : null);

                posts.add(post);
            }
        }
        Collections.sort(posts, new PostComparatorByUpdatedAt_desc());
        return posts;
    }

    public static List<Course> getAllCoursesInUEM() {

        List<Course> courses = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllCourse(filter);
        if (documents == null || documents.size() == 0) {
            return courses;
        } else {
            for (Document document : documents) {
                Course course = new Course();
                course.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                course.setName(document.containsKey("Name") ? document.getString("Name") : null);
                course.setLevel(document.containsKey("Level") ? document.getString("Level") : null);
                course.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                course.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                course.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                course.setExpiring(document.containsKey("Expiring") ? document.getString("Expiring") : null);
                course.setInfo(document.containsKey("info") ? document.getString("info") : null);
                course.setCourseAdmin(document.containsKey("CourseAdmin") ? document.getString("CourseAdmin") : null);

                course.setBatches(document.containsKey("Batches") ? document.getList("Batches", Document.class) : null);
                course.setUniversities(document.containsKey("Universities") ? document.getList("Universities", Document.class) : null);

                course.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                course.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                course.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                course.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                courses.add(course);
            }
        }
        return courses;
    }

    public static List<CourseAdmin> getAllCourseAdmins_UemID(String UemID) {

        List<CourseAdmin> students = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UEM_ID:{$regex:/" + UemID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllCourseAdmin(filter);
        if (documents == null || documents.size() == 0) {
            return students;
        } else {
            for (Document document : documents) {
                CourseAdmin univAdmin = new CourseAdmin();
                univAdmin.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                univAdmin.setUEM_ID(document.containsKey("UEM_ID") ? document.getString("UEM_ID") : null);
                univAdmin.setInfo(document.containsKey("info") ? document.getString("info") : null);
                univAdmin.setDocuments(document.containsKey("Documents") ? document.getList("Documents", Document.class) : null);
                univAdmin.setCourses(document.containsKey("Courses") ? document.getList("Courses", Document.class) : null);
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                students.add(univAdmin);
            }
        }
        return students;
    }

    public static List<Event> getAllEventsInUEM() {

        List<Event> events = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllEvents(filter);
        if (documents == null || documents.size() == 0) {
            return events;
        } else {
            for (Document document : documents) {
                Event event = new Event();
                event.setEventID(document.containsKey("EventID") ? document.getString("EventID") : null);
                event.setTime(document.containsKey("time") ? document.getString("time") : null);
                event.setText(document.containsKey("text") ? document.getString("text") : null);

                event.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                event.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                event.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                events.add(event);
            }
        }
        return events;
    }

    public static List<Message> getAllMessagesInUEM() {

        List<Message> messages = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllMessages(filter);
        if (documents == null || documents.size() == 0) {
            return messages;
        } else {
            for (Document document : documents) {
                Message message= new Message();
                message.setFrom(document.containsKey("From") ? document.getString("From") : null);
                message.setTo(document.containsKey("To") ? document.getString("To") : null);
                message.setText(document.containsKey("text") ? document.getString("text") : null);

                message.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                message.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                message.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                messages.add(message);
            }
        }
        Collections.sort(messages, new MessageComparatorByUpdatedAt());
        return messages;
    }

    public static List<User> getAllMessengersInUEM(String UserID) {

        Set<User> users = new LinkedHashSet<>();
        Set<String> messengers = new HashSet<>();

        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "To:{$regex:/" + UserID + "/}," +
                        "}");

        List<Document> documents = MongoDBUtil.getAllMessages(filter);

        if (documents == null || documents.size() == 0) {
            return new ArrayList<>();
        } else {
            for (Document document : documents) {
                messengers.add(document.getString("From"));
            }

            for (String email : messengers){
                users.addAll(getAllUsers_Email(email));
            }

        }
        List<User> finalUsers = new ArrayList<>(users);
        Collections.sort(finalUsers, new UsersComparatorByUpdatedAt());
        return finalUsers;
    }

    public static List<Message> getAllMessagesInUEM(String MessageID) {

        List<Message> messages = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "MessageID:{$regex:/" + MessageID + "/}," +
                        "}");

        List<Document> documents = MongoDBUtil.getAllMessages(filter);

        if (documents == null || documents.size() == 0) {
            return messages;
        } else {
            for (Document document : documents) {
                Message message= new Message();
                message.setFrom(document.containsKey("From") ? document.getString("From") : null);
                message.setTo(document.containsKey("To") ? document.getString("To") : null);
                message.setText(document.containsKey("text") ? document.getString("text") : null);
                message.setRead(document.containsKey("read") ? document.getString("read") : null);

                message.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                message.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                message.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                messages.add(message);
            }
        }
        Collections.sort(messages, new MessageComparatorByUpdatedAt());
        return messages;
    }

    public static List<Message> getAllMessagesInUEM(String To, boolean read) {

        List<Message> messages = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "}");

        if (read){
            filter = BsonDocument
                    .parse("{ " +
                            "To:{$regex:/" + To + "/}," +
                            "read:{$regex:/" + "Yes" + "/}," +
                            "}");
        }else {
            filter = BsonDocument
                    .parse("{ " +
                            "To:{$regex:/" + To + "/}," +
                            "read:{$regex:/" + "No" + "/}," +
                            "}");
        }

        List<Document> documents = MongoDBUtil.getAllMessages(filter);

        if (documents == null || documents.size() == 0) {
            return messages;
        } else {
            for (Document document : documents) {
                Message message= new Message();
                message.setFrom(document.containsKey("From") ? document.getString("From") : null);
                message.setTo(document.containsKey("To") ? document.getString("To") : null);
                message.setText(document.containsKey("text") ? document.getString("text") : null);
                message.setRead(document.containsKey("read") ? document.getString("read") : null);

                message.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                message.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                message.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                messages.add(message);
            }
        }
        Collections.sort(messages, new MessageComparatorByUpdatedAt());
        return messages;
    }

    public static List<Message> getAllMessagesInUEM(String user1, String user2) {

        List<Message> messages = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "From:{$regex:/" + user1 + "/}," +
                        "To:{$regex:/" + user2 + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllMessages(filter);
        filter = BsonDocument
                .parse("{ " +
                        "From:{$regex:/" + user2 + "/}," +
                        "To:{$regex:/" + user1 + "/}" +
                        "}");
        documents.addAll(MongoDBUtil.getAllMessages(filter));
        if (documents == null || documents.size() == 0) {
            return messages;
        } else {
            for (Document document : documents) {
                Message message= new Message();
                message.setFrom(document.containsKey("From") ? document.getString("From") : null);
                message.setTo(document.containsKey("To") ? document.getString("To") : null);
                message.setText(document.containsKey("text") ? document.getString("text") : null);
                message.setRead(document.containsKey("read") ? document.getString("read") : null);

                message.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                message.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                message.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                messages.add(message);
            }
        }
        Collections.sort(messages, new MessageComparatorByUpdatedAt());
        return messages;
    }

    public static List<Batch> getAllBatchesInUEM() {

        List<Batch> batches = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllBatch(filter);
        if (documents == null || documents.size() == 0) {
            return batches;
        } else {
            for (Document document : documents) {
                Batch batch = new Batch();
                batch.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                batch.setBatchID(document.containsKey("BatchID") ? document.getString("CourseID") : null);
                batch.setDuration(document.containsKey("Duration") ? document.getString("Duration") : null);
                batch.setSpanOver(document.containsKey("SpanOver") ? document.getString("SpanOver") : null);
                batch.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                batch.setCompletion(document.containsKey("Completion") ? document.getString("Completion") : null);

                batch.setLeadTutors(document.containsKey("LeadTutors") ? document.getList("LeadTutors", Document.class) : null);
                batch.setFellowTutors(document.containsKey("FellowTutors") ? document.getList("FellowTutors", Document.class) : null);
                batch.setStudents(document.containsKey("Students") ? document.getList("Students", Document.class) : null);
                batch.setActionLogs(document.containsKey("ActionLogs") ? document.getList("ActionLogs", Document.class) : null);
                batch.setBatchRequests(document.containsKey("BatchRequests") ? document.getList("BatchRequests", Document.class) : null);

                batch.setInfo(document.containsKey("info") ? document.get("info", Document.class) : null);
                batch.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                batch.setCalendar(document.containsKey("Calendar") ? document.get("Calendar", Document.class) : null);
                batch.setAdminID(document.containsKey("AdminID") ? document.getString("AdminID") : null);

                batch.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                batch.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                batch.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                batch.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                batch.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                batches.add(batch);
            }
        }
        return batches;
    }

    public static List<Batch> getAllBatchesInUEM_ByCourseID_UnivID_StateRequested_BatchRequestsExists(String CourseID, String UnivID) {

        List<Batch> batches = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "CourseID:{$regex:/" + CourseID + "/}," +
                        "UnivID:{$regex:/" + UnivID + "/}," +
                        "BatchRequests:{$exists:true}" +
                        "'Status.status':'requested'" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllBatch(filter);
        if (documents == null || documents.size() == 0) {
            return batches;
        } else {
            for (Document document : documents) {
                Batch batch = new Batch();
                batch.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                batch.setBatchID(document.containsKey("BatchID") ? document.getString("CourseID") : null);
                batch.setDuration(document.containsKey("Duration") ? document.getString("Duration") : null);
                batch.setSpanOver(document.containsKey("SpanOver") ? document.getString("SpanOver") : null);
                batch.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                batch.setCompletion(document.containsKey("Completion") ? document.getString("Completion") : null);

                batch.setLeadTutors(document.containsKey("LeadTutors") ? document.getList("LeadTutors", Document.class) : null);
                batch.setFellowTutors(document.containsKey("FellowTutors") ? document.getList("FellowTutors", Document.class) : null);
                batch.setStudents(document.containsKey("Students") ? document.getList("Students", Document.class) : null);
                batch.setActionLogs(document.containsKey("ActionLogs") ? document.getList("ActionLogs", Document.class) : null);
                batch.setBatchRequests(document.containsKey("BatchRequests") ? document.getList("BatchRequests", Document.class) : null);

                batch.setInfo(document.containsKey("info") ? document.get("info", Document.class) : null);
                batch.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                batch.setCalendar(document.containsKey("Calendar") ? document.get("Calendar", Document.class) : null);
                batch.setAdminID(document.containsKey("AdminID") ? document.getString("AdminID") : null);

                batch.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                batch.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                batch.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                batch.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                batch.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                batches.add(batch);
            }
        }
        return batches;
    }

    public static List<Batch> getAllBatchesInUEM_ByBatchID(String BatchID) {

        List<Batch> batches = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "BatchID:{$regex:/" + BatchID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllBatch(filter);
        if (documents == null || documents.size() == 0) {
            return batches;
        } else {
            for (Document document : documents) {
                Batch batch = new Batch();
                batch.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                batch.setBatchID(document.containsKey("BatchID") ? document.getString("CourseID") : null);
                batch.setDuration(document.containsKey("Duration") ? document.getString("Duration") : null);
                batch.setSpanOver(document.containsKey("SpanOver") ? document.getString("SpanOver") : null);
                batch.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                batch.setCompletion(document.containsKey("Completion") ? document.getString("Completion") : null);

                batch.setLeadTutors(document.containsKey("LeadTutors") ? document.getList("LeadTutors", Document.class) : null);
                batch.setFellowTutors(document.containsKey("FellowTutors") ? document.getList("FellowTutors", Document.class) : null);
                batch.setStudents(document.containsKey("Students") ? document.getList("Students", Document.class) : null);
                batch.setActionLogs(document.containsKey("ActionLogs") ? document.getList("ActionLogs", Document.class) : null);
                batch.setBatchRequests(document.containsKey("BatchRequests") ? document.getList("BatchRequests", Document.class) : null);

                batch.setInfo(document.containsKey("info") ? document.get("info", Document.class) : null);
                batch.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                batch.setCalendar(document.containsKey("Calendar") ? document.get("Calendar", Document.class) : null);
                batch.setAdminID(document.containsKey("AdminID") ? document.getString("AdminID") : null);

                batch.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                batch.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                batch.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                batch.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                batch.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                batches.add(batch);
            }
        }
        return batches;
    }

    public static List<Batch> getAllBatchesInUEM_ByAdminID(String AdminID) {

        List<Batch> batches = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "AdminID:{$regex:/" + AdminID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllBatch(filter);
        if (documents == null || documents.size() == 0) {
            return batches;
        } else {
            for (Document document : documents) {
                Batch batch = new Batch();
                batch.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                batch.setBatchID(document.containsKey("BatchID") ? document.getString("CourseID") : null);
                batch.setDuration(document.containsKey("Duration") ? document.getString("Duration") : null);
                batch.setSpanOver(document.containsKey("SpanOver") ? document.getString("SpanOver") : null);
                batch.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                batch.setCompletion(document.containsKey("Completion") ? document.getString("Completion") : null);

                batch.setLeadTutors(document.containsKey("LeadTutors") ? document.getList("LeadTutors", Document.class) : null);
                batch.setFellowTutors(document.containsKey("FellowTutors") ? document.getList("FellowTutors", Document.class) : null);
                batch.setStudents(document.containsKey("Students") ? document.getList("Students", Document.class) : null);
                batch.setActionLogs(document.containsKey("ActionLogs") ? document.getList("ActionLogs", Document.class) : null);
                batch.setBatchRequests(document.containsKey("BatchRequests") ? document.getList("BatchRequests", Document.class) : null);

                batch.setInfo(document.containsKey("info") ? document.get("info", Document.class) : null);
                batch.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                batch.setCalendar(document.containsKey("Calendar") ? document.get("Calendar", Document.class) : null);
                batch.setAdminID(document.containsKey("AdminID") ? document.getString("AdminID") : null);

                batch.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                batch.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                batch.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                batch.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                batch.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                batches.add(batch);
            }
        }
        return batches;
    }

    public static List<Batch> getAllBatchesInUEM_ByCourseID(String CourseID) {

        List<Batch> batches = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{" +
                        "CourseID:{$regex:/" + CourseID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllBatch(filter);
        if (documents == null || documents.size() == 0) {
            return batches;
        } else {
            for (Document document : documents) {
                Batch batch = new Batch();
                batch.setCourseID(document.containsKey("CourseID") ? document.getString("CourseID") : null);
                batch.setBatchID(document.containsKey("BatchID") ? document.getString("CourseID") : null);
                batch.setDuration(document.containsKey("Duration") ? document.getString("Duration") : null);
                batch.setSpanOver(document.containsKey("SpanOver") ? document.getString("SpanOver") : null);
                batch.setStarting(document.containsKey("Starting") ? document.getString("Starting") : null);
                batch.setCompletion(document.containsKey("Completion") ? document.getString("Completion") : null);

                batch.setLeadTutors(document.containsKey("LeadTutors") ? document.getList("LeadTutors", Document.class) : null);
                batch.setFellowTutors(document.containsKey("FellowTutors") ? document.getList("FellowTutors", Document.class) : null);
                batch.setStudents(document.containsKey("Students") ? document.getList("Students", Document.class) : null);
                batch.setActionLogs(document.containsKey("ActionLogs") ? document.getList("ActionLogs", Document.class) : null);
                batch.setBatchRequests(document.containsKey("BatchRequests") ? document.getList("BatchRequests", Document.class) : null);

                batch.setInfo(document.containsKey("info") ? document.get("info", Document.class) : null);
                batch.setBilling(document.containsKey("Billing") ? document.get("Billing", Document.class) : null);
                batch.setCalendar(document.containsKey("Calendar") ? document.get("Calendar", Document.class) : null);
                batch.setAdminID(document.containsKey("AdminID") ? document.getString("AdminID") : null);

                batch.setStatus(document.containsKey("Status") ? document.get("Status", Document.class) : null);
                batch.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                batch.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                batch.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                batch.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                batches.add(batch);
            }
        }
        return batches;
    }

    public static List<UnivAdmin> getAllAdmin_UserID(String UserID) {

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
                univAdmin.setUnivID(document.containsKey("UnivID") ? document.getString("UnivID") : null);

                univAdmin.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                univAdmin.setUEM_ID(document.containsKey("UEM_ID") ? document.getString("UEM_ID") : null);
                univAdmin.setInfo(document.containsKey("info") ? document.getString("info") : null);
                univAdmin.setDocuments(document.containsKey("Documents") ? document.getList("Documents", Document.class) : null);
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                univAdmins.add(univAdmin);
            }
        }
        return univAdmins;
    }

    public static List<UnivAdmin> getAllAdmin_UemID(String UemID) {

        List<UnivAdmin> univAdmins = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UEM_ID:{$regex:/" + UemID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllUniversityAdmin(filter);
        if (documents == null || documents.size() == 0) {
            return univAdmins;
        } else {
            for (Document document : documents) {
                UnivAdmin univAdmin = new UnivAdmin();
                univAdmin.setUnivID(document.containsKey("UnivID") ? document.getString("UnivID") : null);

                univAdmin.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                univAdmin.setUEM_ID(document.containsKey("UEM_ID") ? document.getString("UEM_ID") : null);
                univAdmin.setInfo(document.containsKey("info") ? document.getString("info") : null);
                univAdmin.setDocuments(document.containsKey("Documents") ? document.getList("Documents", Document.class) : null);
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                univAdmins.add(univAdmin);
            }
        }
        return univAdmins;
    }

    public static List<Student> getAllStudents_UemID(String UemID) {

        List<Student> students = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UEM_ID:{$regex:/" + UemID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllStudents(filter);
        if (documents == null || documents.size() == 0) {
            return students;
        } else {
            for (Document document : documents) {
                Student univAdmin = new Student();
                univAdmin.setUnivID(document.containsKey("UnivID") ? document.getString("UnivID") : null);

                univAdmin.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                univAdmin.setUEM_ID(document.containsKey("UEM_ID") ? document.getString("UEM_ID") : null);
                univAdmin.setInfo(document.containsKey("info") ? document.getString("info") : null);
                univAdmin.setDocuments(document.containsKey("Documents") ? document.getList("Documents", Document.class) : null);
                univAdmin.setBatches(document.containsKey("Batches") ? document.getList("Batches", Document.class) : null);
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                students.add(univAdmin);
            }
        }
        return students;
    }

    public static List<Teacher> getAllTeachers_UemID(String UemID) {

        List<Teacher> teachers = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "UEM_ID:{$regex:/" + UemID + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllTeachers(filter);
        if (documents == null || documents.size() == 0) {
            return teachers;
        } else {
            for (Document document : documents) {
                Teacher univAdmin = new Teacher();
                univAdmin.setUnivID(document.containsKey("UnivID") ? document.getString("UnivID") : null);

                univAdmin.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                univAdmin.setUEM_ID(document.containsKey("UEM_ID") ? document.getString("UEM_ID") : null);
                univAdmin.setInfo(document.containsKey("info") ? document.getString("info") : null);
                univAdmin.setDocuments(document.containsKey("Documents") ? document.getList("Documents", Document.class) : null);
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                teachers.add(univAdmin);
            }
        }
        return teachers;
    }

    public static List<User> getAllUsers_Email(String email) {

        List<User> users = new ArrayList<>();
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "Email:{$regex:/" + email + "/}" +
                        "}");
        List<Document> documents = MongoDBUtil.getAllUniversalUsers(filter);

        Logs logs = new Logs();
        logs.setFrom("AllDBOperations");
        logs.setText(documents.toString());
        logs.setLevel("info");
        AllDBOperations.createLogs(logs);

        if (documents == null || documents.size() == 0) {
            return users;
        } else {
            for (Document document : documents) {
                try {
                    User user = new User();
                    user.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                    user.setDOB(document.containsKey("DOB") ? document.getString("DOB") : null);
                    user.setAddress(document.containsKey("Address") ? document.getString("Address") : null);

                    user.setPhoto((
                            document.containsKey("Photo")
                                    ? document.get("Photo", Document.class)
                                    : new Document()));
                    user.setMobile(document.containsKey("Mobile") ? document.getString("Mobile") : null);

                    user.setName(document.containsKey("Name") ? document.getString("Name") : null);
                    user.setPassword(document.containsKey("Password") ? document.getString("Password") : null);
                    user.setEmail(document.containsKey("Email") ? document.getString("Email") : null);
                    user.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);

                    user.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                    user.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                    users.add(user);
                } catch (Exception e) {
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
                    user.setUserID(document.containsKey("UserID") ? document.getString("UserID") : null);
                    user.setDOB(document.containsKey("DOB") ? document.getString("DOB") : null);
                    user.setAddress(document.containsKey("Address") ? document.getString("Address") : null);

                    user.setPhoto((
                            document.containsKey("Photo")
                                    ? document.get("Photo", Document.class)
                                    : new Document()));
                    user.setMobile(document.containsKey("Mobile") ? document.getString("Mobile") : null);

                    user.setName(document.containsKey("Name") ? document.getString("Name") : null);
                    user.setPassword(document.containsKey("Password") ? document.getString("Password") : null);
                    user.setEmail(document.containsKey("Email") ? document.getString("Email") : null);
                    user.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);

                    user.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                    user.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                    users.add(user);
                } catch (Exception e) {
                    logger.debug(UtilsManager.exceptionAsString(e));
                }
            }
        }
        return users;
    }

}