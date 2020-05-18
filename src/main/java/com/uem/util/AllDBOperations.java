package com.uem.util;

import com.uem.model.*;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

    /*

    Check this link to understand more about finding teh substrings and regex matches in Mongo DB
    https://docs.mongodb.com/manual/reference/operator/query/regex/#examples

    */

public class AllDBOperations {

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

    public static Map<String, Object> updateUniversity(University university, JSONObject body, Boolean append) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", false);
        try {

            if (append){

                University toBeAppended = UtilsManager.jsonToUniversity(body);
                if (toBeAppended.getActionLogs() != null && toBeAppended.getActionLogs().size() > 0){
                    toBeAppended.getActionLogs().addAll(university.getActionLogs());
                }
                if (toBeAppended.getCourses() != null && toBeAppended.getCourses().size() > 0){
                    toBeAppended.getCourses().addAll(university.getCourses());
                }
                if (toBeAppended.getStudents() != null && toBeAppended.getStudents().size() > 0){
                    toBeAppended.getStudents().addAll(university.getStudents());
                }
                if (toBeAppended.getTeachers() != null && toBeAppended.getTeachers().size() > 0){
                    toBeAppended.getTeachers().addAll(university.getTeachers());
                }
                if (toBeAppended.getUnivAdmins() != null && toBeAppended.getUnivAdmins().size() > 0){
                    toBeAppended.getUnivAdmins().addAll(university.getUnivAdmins());
                }

                body = UtilsManager.universityToJson(toBeAppended);

            }

            // update ActionLogs
            JSONObject jsonObject = UtilsManager.universityToJson(university);
            JSONArray array = jsonObject.getJSONArray("ActionLogs");
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

    public static Map<String, Object> updateAdmin(UnivAdmin univAdmin, JSONObject body, Boolean append) {
        return null;
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
                univAdmin.setObjectID(document.containsKey("_id") ? document.getString("_id") : null);
                univAdmin.set_created_at(document.containsKey("_created_at") ? document.getDate("_created_at") : null);
                univAdmin.set_updated_at(document.containsKey("_updated_at") ? document.getDate("_updated_at") : null);

                univAdmin.setPhoto(document.containsKey("Photo") ? document.get("Photo", Document.class) : null);

                students.add(univAdmin);
            }
        }
        return students;
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
                }catch (Exception e){
                    logger.debug(UtilsManager.exceptionAsString(e));
                }
            }
        }
        return users;
    }

}