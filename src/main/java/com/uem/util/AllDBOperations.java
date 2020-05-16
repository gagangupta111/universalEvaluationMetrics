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

    public static Boolean updateUniversity(University university, JSONObject body, Boolean append) {
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
                university.setUnivID(document.getString("UnivID"));
                university.setName((document.getString("Name")));
                university.setStarted((document.getString("Started")));
                university.setUnivAdmins((document.getList("UnivAdmins", String.class)));
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
                university.setUnivAdmins((document.getList("UnivAdmins", String.class)));
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

}