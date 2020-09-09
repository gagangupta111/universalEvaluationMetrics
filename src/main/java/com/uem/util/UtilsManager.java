package com.uem.util;

import com.uem.model.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class UtilsManager {

    static Logger logger = LogUtil.getInstance();

    public static JSONArray documentListToJsonArray(List<Document> documents) {

        JSONArray array = new JSONArray();

        for (Document document : documents) {
            JSONObject object = new JSONObject();
            for (String key : document.keySet()) {
                try {
                    object.put(key, document.get(key));
                } catch (JSONException e) {
                    logger.debug(UtilsManager.exceptionAsString(e));
                }
            }
            array.put(object);
        }

        return array;
    }

    public static List<Document> jsonArrayToDocumentList(JSONArray jsonArray) {

        List<Document> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = new JSONObject(i);
            Document document = new Document();

            Iterator<String> keys = object.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                try {
                    document.put(key, object.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            list.add(document);
        }

        return list;
    }

    public static List<Document> mergeDocuments(List<Document> original, List<Document> newList, String param) {

        Map<String, Document> newDocuemtns = new HashMap<>();

        for (Document originalDoc : original) {
            if (originalDoc.get(param) != null) {
                newDocuemtns.put(String.valueOf(originalDoc.get(param)), originalDoc);
            } else {
                newDocuemtns.put(String.valueOf(Math.random()), originalDoc);
            }
        }

        for (Document newDoc : newList) {
            if (newDoc.get(param) != null) {
                newDocuemtns.put(String.valueOf(newDoc.get(param)), newDoc);
            } else {
                newDocuemtns.put(String.valueOf(Math.random()), newDoc);
            }
        }
        List<Document> updated = new ArrayList<>();
        for (String key : newDocuemtns.keySet()) {
            updated.add(newDocuemtns.get(key));
        }
        return updated;

    }

    public static Document deleteKeysFromDocument(Document original, String[] keyToBeDeleted) {

        for (String key : keyToBeDeleted) {
            if (original.containsKey(key)) {
                original.remove(key);
            }
        }

        return original;

    }

    public static List<Document> deleteDocuments(List<Document> original, String[] toBeDeleted, String param) {

        Map<String, Document> newDocuments = new HashMap<>();

        for (Document originalDoc : original) {
            if (originalDoc.get(param) != null) {
                newDocuments.put(String.valueOf(originalDoc.get(param)), originalDoc);
            } else {
                newDocuments.put(String.valueOf(Math.random()), originalDoc);
            }
        }

        for (String key : toBeDeleted) {
            if (newDocuments.get(key) != null) {
                newDocuments.remove(String.valueOf(key));
            }
        }
        List<Document> updated = new ArrayList<>();
        for (String key : newDocuments.keySet()) {
            updated.add(newDocuments.get(key));
        }
        return updated;

    }

    public static void multipartFileToFile(MultipartFile file, Path dir) throws Exception {
        Path filepath = Paths.get(dir.toString(), file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }
    }

    public static Batch jsonToBatch(JSONObject jsonObject) {

        Batch batch = new Batch();
        try {
            batch.setBatchID(jsonObject.has("BatchID") ? jsonObject.getString("BatchID") : null);
            batch.setCourseID(jsonObject.has("CourseID") ? jsonObject.getString("CourseID") : null);
            batch.setDuration(jsonObject.has("Duration") ? jsonObject.getString("Duration") : null);
            batch.setSpanOver(jsonObject.has("SpanOver") ? jsonObject.getString("SpanOver") : null);
            batch.setSpanOver(jsonObject.has("Starting") ? jsonObject.getString("Starting") : null);
            batch.setSpanOver(jsonObject.has("Completion") ? jsonObject.getString("Completion") : null);

            List<Document> BatchRequests = new ArrayList<>();
            JSONArray array = jsonObject.has("BatchRequests") ? jsonObject.getJSONArray("BatchRequests") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                BatchRequests.add(Document.parse(String.valueOf(array.get(i))));
            }
            batch.setBatchRequests(BatchRequests);

            List<Document> LeadTutors = new ArrayList<>();
            array = jsonObject.has("LeadTutors") ? jsonObject.getJSONArray("LeadTutors") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                LeadTutors.add(Document.parse(String.valueOf(array.get(i))));
            }
            batch.setLeadTutors(LeadTutors);

            List<Document> FellowTutors = new ArrayList<>();
            array = jsonObject.has("FellowTutors") ? jsonObject.getJSONArray("FellowTutors") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                FellowTutors.add(Document.parse(String.valueOf(array.get(i))));
            }
            batch.setFellowTutors(FellowTutors);

            List<Document> Students = new ArrayList<>();
            array = jsonObject.has("Students") ? jsonObject.getJSONArray("Students") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                Students.add(Document.parse(String.valueOf(array.get(i))));
            }
            batch.setStudents(Students);

            Document info = new Document();
            JSONObject object = jsonObject.has("info") ? jsonObject.getJSONObject("info") : new JSONObject();
            info = Document.parse(object.toString());
            batch.setInfo(info);

            Document Billing = new Document();
            object = jsonObject.has("Billing") ? jsonObject.getJSONObject("Billing") : new JSONObject();
            Billing = Document.parse(object.toString());
            batch.setBilling(Billing);

            Document Calendar = new Document();
            object = jsonObject.has("Calendar") ? jsonObject.getJSONObject("Calendar") : new JSONObject();
            Calendar = Document.parse(object.toString());
            batch.setCalendar(Calendar);

            Document Status = new Document();
            object = jsonObject.has("Status") ? jsonObject.getJSONObject("Status") : new JSONObject();
            Status = Document.parse(object.toString());
            batch.setCalendar(Status);

            List<Document> actionLogs = new ArrayList<>();
            array = jsonObject.has("ActionLogs") ? jsonObject.getJSONArray("ActionLogs") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                actionLogs.add(Document.parse(String.valueOf(array.get(i))));
            }
            batch.setActionLogs(actionLogs);

            batch.setObjectID(jsonObject.has("AdminID") ? jsonObject.getString("AdminID") : null);

            Document photo = new Document();
            object = jsonObject.has("Photo") ? jsonObject.getJSONObject("Photo") : new JSONObject();
            photo = Document.parse(object.toString());
            batch.setPhoto(photo);

            batch.setObjectID(jsonObject.has("_id") ? jsonObject.getString("_id") : null);
            batch.set_created_at(jsonObject.has("_created_at") ? Date.from(Instant.parse(jsonObject.getString("_created_at"))) : null);
            batch.set_updated_at(jsonObject.has("_updated_at") ? Date.from(Instant.parse(jsonObject.getString("_updated_at"))) : null);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return batch;
    }

    public static JSONObject batchToJson(Batch batch) {

        JSONObject object = new JSONObject();
        try {
            object = batch.getBatchID() != null ? object.put("BatchID", batch.getBatchID()) : object;
            object = batch.getCourseID() != null ? object.put("CourseID", batch.getCourseID()) : object;
            object = batch.getDuration() != null ? object.put("Duration", batch.getDuration()) : object;
            object = batch.getSpanOver() != null ? object.put("SpanOver", batch.getSpanOver()) : object;
            object = batch.getStarting() != null ? object.put("Starting", batch.getStarting()) : object;
            object = batch.getCompletion() != null ? object.put("Completion", batch.getCompletion()) : object;

            List<Document> BatchRequests = batch.getBatchRequests() != null ? batch.getBatchRequests() : new ArrayList<>();
            JSONArray array = new JSONArray();
            for (Document document : BatchRequests) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("BatchRequests", array) : object;

            List<Document> students = batch.getStudents() != null ? batch.getStudents() : new ArrayList<>();
            array = new JSONArray();
            for (Document document : students) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Students", array) : object;

            List<Document> FellowTutors = batch.getFellowTutors() != null ? batch.getFellowTutors() : new ArrayList<>();
            array = new JSONArray();
            for (Document document : FellowTutors) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("FellowTutors", array) : object;

            List<Document> LeadTutors = batch.getLeadTutors() != null ? batch.getLeadTutors() : new ArrayList<>();
            array = new JSONArray();
            for (Document document : LeadTutors) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("LeadTutors", array) : object;

            Document info = batch.getInfo();
            object = info != null && info.size() > 0 ? object.put("info", new JSONObject(info.toJson())) : object;

            Document Billing = batch.getBilling();
            object = Billing != null && Billing.size() > 0 ? object.put("Billing", new JSONObject(Billing.toJson())) : object;

            Document Calendar = batch.getCalendar();
            object = Calendar != null && Calendar.size() > 0 ? object.put("Calendar", new JSONObject(Calendar.toJson())) : object;

            Document Status = batch.getStatus();
            object = Status != null && Status.size() > 0 ? object.put("Status", new JSONObject(Status.toJson())) : object;

            object = batch.getAdminID() != null ? object.put("AdminID", batch.getAdminID()) : object;

            List<Document> actionLogs = batch.getActionLogs() != null ? batch.getActionLogs() : new ArrayList<>();
            array = new JSONArray();
            for (Document document : actionLogs) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("ActionLogs", array) : object;

            Document photo = batch.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;

            object = batch.getObjectID() != null ? object.put("_id", batch.getObjectID()) : object;
            object = batch.get_created_at() != null ? object.put("_created_at", batch.get_created_at()) : object;
            object = batch.get_updated_at() != null ? object.put("_updated_at", batch.get_updated_at()) : object;

        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static University jsonToUniversity(JSONObject jsonObject) {

        University university = new University();
        try {
            university.setUnivID(jsonObject.has("UnivID") ? jsonObject.getString("UnivID") : null);
            university.setName(jsonObject.has("Name") ? jsonObject.getString("Name") : null);
            university.setStarted(jsonObject.has("Started") ? jsonObject.getString("Started") : null);

            List<String> univAdmins = new ArrayList<>();
            JSONArray array = jsonObject.has("UnivAdmins") ? jsonObject.getJSONArray("UnivAdmins") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                univAdmins.add(String.valueOf(array.get(i)));
            }
            university.setUnivAdmins(univAdmins);

            List<Document> students = new ArrayList<>();
            array = jsonObject.has("Students") ? jsonObject.getJSONArray("Students") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                students.add(Document.parse(String.valueOf(array.get(i))));
            }
            university.setStudents(students);

            List<Document> teachers = new ArrayList<>();
            array = jsonObject.has("Teachers") ? jsonObject.getJSONArray("Teachers") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                teachers.add(Document.parse(String.valueOf(array.get(i))));
            }
            university.setTeachers(teachers);

            List<Document> courses = new ArrayList<>();
            array = jsonObject.has("Courses") ? jsonObject.getJSONArray("Courses") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                courses.add(Document.parse(String.valueOf(array.get(i))));
            }
            university.setCourses(courses);

            university.setWebsite((jsonObject.has("Website") ? jsonObject.getString("Website") : null));

            Document legalInfo = new Document();
            JSONObject object = jsonObject.has("LegalInfo") ? jsonObject.getJSONObject("LegalInfo") : new JSONObject();
            legalInfo = Document.parse(object.toString());
            university.setLegalInfo(legalInfo);

            Document moreinfo = new Document();
            object = jsonObject.has("MoreInfo") ? jsonObject.getJSONObject("MoreInfo") : new JSONObject();
            moreinfo = Document.parse(object.toString());
            university.setMoreInfo(moreinfo);

            List<Document> actionLogs = new ArrayList<>();
            array = jsonObject.has("ActionLogs") ? jsonObject.getJSONArray("ActionLogs") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                actionLogs.add(Document.parse(String.valueOf(array.get(i))));
            }
            university.setActionLogs(actionLogs);

            university.setInfo((jsonObject.has("info") ? jsonObject.getString("info") : null));

            Document photo = new Document();
            object = jsonObject.has("Photo") ? jsonObject.getJSONObject("Photo") : new JSONObject();
            photo = Document.parse(object.toString());
            university.setPhoto(photo);

            university.setObjectID(jsonObject.has("_id") ? jsonObject.getString("_id") : null);
            university.set_created_at(jsonObject.has("_created_at") ? Date.from(Instant.parse(jsonObject.getString("_created_at"))) : null);
            university.set_updated_at(jsonObject.has("_updated_at") ? Date.from(Instant.parse(jsonObject.getString("_updated_at"))) : null);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return university;
    }

    public static JSONObject universityToJson(University university) {

        JSONObject object = new JSONObject();
        try {
            object = university.getUnivID() != null ? object.put("UnivID", university.getUnivID()) : object;
            object = university.getName() != null ? object.put("Name", university.getName()) : object;
            object = university.getStarted() != null ? object.put("Started", university.getStarted()) : object;

            List<String> univAdmins = university.getUnivAdmins();
            JSONArray array = new JSONArray();
            for (String univAdmin : univAdmins) {
                array.put(univAdmin);
            }
            object = array.length() > 0 ? object.put("UnivAdmins", array) : object;

            List<Document> students = university.getStudents();
            array = new JSONArray();
            for (Document document : students) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Students", array) : object;

            List<Document> teachers = university.getTeachers();
            array = new JSONArray();
            for (Document document : teachers) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Teachers", array) : object;

            List<Document> courses = university.getCourses();
            array = new JSONArray();
            for (Document document : courses) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Courses", array) : object;

            object = university.getWebsite() != null ? object.put("Website", university.getWebsite()) : object;

            Document legalInfo = university.getLegalInfo();
            object = legalInfo != null && legalInfo.size() > 0 ? object.put("LegalInfo", new JSONObject(legalInfo.toJson())) : object;

            Document moreInfo = university.getMoreInfo();
            object = moreInfo != null && moreInfo.size() > 0 ? object.put("MoreInfo", new JSONObject(moreInfo.toJson())) : object;

            List<Document> actionLogs = university.getActionLogs();
            array = new JSONArray();
            for (Document document : actionLogs) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("ActionLogs", array) : object;

            Document info = university.getMoreInfo();
            object = info != null && info.size() > 0 ? object.put("info", new JSONObject(info.toJson())) : object;

            Document photo = university.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;

            object = university.getObjectID() != null ? object.put("_id", university.getObjectID()) : object;
            object = university.get_created_at() != null ? object.put("_created_at", university.get_created_at()) : object;
            object = university.get_updated_at() != null ? object.put("_updated_at", university.get_updated_at()) : object;

        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static JSONObject courseAdminToJson(CourseAdmin courseAdmin) {

        JSONObject object = new JSONObject();
        try {


            object = courseAdmin.getUEM_ID() != null ? object.put("UEM_ID", courseAdmin.getUEM_ID()) : object;
            object = courseAdmin.getUserID() != null ? object.put("UserID", courseAdmin.getUserID()) : object;
            object = courseAdmin.getInfo() != null ? object.put("info", courseAdmin.getInfo()) : object;

            object = courseAdmin.getObjectID() != null ? object.put("_id", courseAdmin.getObjectID()) : object;
            object = courseAdmin.get_created_at() != null ? object.put("_created_at", courseAdmin.get_created_at()) : object;
            object = courseAdmin.get_updated_at() != null ? object.put("_updated_at", courseAdmin.get_updated_at()) : object;

            List<Document> courses = courseAdmin.getCourses();
            JSONArray array = new JSONArray();
            for (Document document : courses) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Courses", array) : object;

            List<Document> documents = courseAdmin.getDocuments();
            array = new JSONArray();
            for (Document document : documents) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Documents", array) : object;

            Document photo = courseAdmin.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static JSONObject userToJson(User user) {

        JSONObject object = new JSONObject();
        try {

            object = user.getUserID() != null ? object.put("UserID", user.getUserID()) : object;
            object = user.getInfo() != null ? object.put("info", user.getInfo()) : object;
            object = user.getPassword() != null ? object.put("Password", user.getPassword()) : object;
            object = user.getAddress() != null ? object.put("Address", user.getAddress()) : object;
            object = user.getDOB() != null ? object.put("DOB", user.getDOB()) : object;
            object = user.getEmail() != null ? object.put("Email", user.getEmail()) : object;
            object = user.getMobile() != null ? object.put("Mobile", user.getMobile()) : object;
            object = user.getName() != null ? object.put("Name", user.getName()) : object;

            object = user.getObjectID() != null ? object.put("_id", user.getObjectID()) : object;
            object = user.get_created_at() != null ? object.put("_created_at", user.get_created_at()) : object;
            object = user.get_updated_at() != null ? object.put("_updated_at", user.get_updated_at()) : object;

            Document photo = user.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static JSONObject adminToJson(UnivAdmin univAdmin) {

        JSONObject object = new JSONObject();
        try {

            object = univAdmin.getUnivID() != null ? object.put("UnivID", univAdmin.getUnivID()) : object;
            object = univAdmin.getUEM_ID() != null ? object.put("UEM_ID", univAdmin.getUEM_ID()) : object;
            object = univAdmin.getUserID() != null ? object.put("UserID", univAdmin.getUserID()) : object;
            object = univAdmin.getInfo() != null ? object.put("info", univAdmin.getInfo()) : object;

            object = univAdmin.getObjectID() != null ? object.put("_id", univAdmin.getObjectID()) : object;
            object = univAdmin.get_created_at() != null ? object.put("_created_at", univAdmin.get_created_at()) : object;
            object = univAdmin.get_updated_at() != null ? object.put("_updated_at", univAdmin.get_updated_at()) : object;

            List<Document> documents = univAdmin.getDocuments();
            JSONArray array = new JSONArray();
            for (Document document : documents) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Documents", array) : object;

            Document photo = univAdmin.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static UnivAdmin jsonToAdmin(JSONObject jsonObject) {

        UnivAdmin univAdmin = new UnivAdmin();

        try {
            univAdmin.setUnivID(jsonObject.has("UnivID") ? jsonObject.getString("UnivID") : null);
            univAdmin.setUEM_ID(jsonObject.has("UEM_ID") ? jsonObject.getString("UEM_ID") : null);
            univAdmin.setUserID(jsonObject.has("UserID") ? jsonObject.getString("UserID") : null);
            univAdmin.setInfo(jsonObject.has("info") ? jsonObject.getString("info") : null);

            List<Document> courses = new ArrayList<>();
            JSONArray array = jsonObject.has("Documents") ? jsonObject.getJSONArray("Documents") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                courses.add(Document.parse(String.valueOf(array.get(i))));
            }
            univAdmin.setDocuments(courses);

            Document photo = new Document();
            JSONObject object = jsonObject.has("Photo") ? jsonObject.getJSONObject("Photo") : new JSONObject();
            photo = Document.parse(object.toString());
            univAdmin.setPhoto(photo);

            univAdmin.setObjectID(jsonObject.has("_id") ? jsonObject.getString("_id") : null);
            univAdmin.set_created_at(jsonObject.has("_created_at") ? Date.from(Instant.parse(jsonObject.getString("_created_at"))) : null);
            univAdmin.set_updated_at(jsonObject.has("_updated_at") ? Date.from(Instant.parse(jsonObject.getString("_updated_at"))) : null);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return univAdmin;
    }

    public static JSONObject studentToJson(Student student) {

        JSONObject object = new JSONObject();
        try {

            object = student.getUnivID() != null ? object.put("UnivID", student.getUnivID()) : object;
            object = student.getUEM_ID() != null ? object.put("UEM_ID", student.getUEM_ID()) : object;
            object = student.getUserID() != null ? object.put("UserID", student.getUserID()) : object;
            object = student.getInfo() != null ? object.put("info", student.getInfo()) : object;

            object = student.getObjectID() != null ? object.put("_id", student.getObjectID()) : object;
            object = student.get_created_at() != null ? object.put("_created_at", student.get_created_at()) : object;
            object = student.get_updated_at() != null ? object.put("_updated_at", student.get_updated_at()) : object;

            List<Document> batches = student.getBatches();
            JSONArray array = new JSONArray();
            for (Document document : batches) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Batches", array) : object;

            List<Document> documents = student.getDocuments();
            array = new JSONArray();
            for (Document document : documents) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Documents", array) : object;

            Document photo = student.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static Student jsonToStudent(JSONObject jsonObject) {

        Student univAdmin = new Student();

        try {
            univAdmin.setUnivID(jsonObject.has("UnivID") ? jsonObject.getString("UnivID") : null);
            univAdmin.setUEM_ID(jsonObject.has("UEM_ID") ? jsonObject.getString("UEM_ID") : null);
            univAdmin.setUserID(jsonObject.has("UserID") ? jsonObject.getString("UserID") : null);
            univAdmin.setInfo(jsonObject.has("info") ? jsonObject.getString("info") : null);

            List<Document> batches = new ArrayList<>();
            JSONArray array = jsonObject.has("Batches") ? jsonObject.getJSONArray("Batches") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                batches.add(Document.parse(String.valueOf(array.get(i))));
            }
            univAdmin.setBatches(batches);

            List<Document> courses = new ArrayList<>();
            array = jsonObject.has("Documents") ? jsonObject.getJSONArray("Documents") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                courses.add(Document.parse(String.valueOf(array.get(i))));
            }
            univAdmin.setDocuments(courses);

            Document photo = new Document();
            JSONObject object = jsonObject.has("Photo") ? jsonObject.getJSONObject("Photo") : new JSONObject();
            photo = Document.parse(object.toString());
            univAdmin.setPhoto(photo);

            univAdmin.setObjectID(jsonObject.has("_id") ? jsonObject.getString("_id") : null);
            univAdmin.set_created_at(jsonObject.has("_created_at") ? Date.from(Instant.parse(jsonObject.getString("_created_at"))) : null);
            univAdmin.set_updated_at(jsonObject.has("_updated_at") ? Date.from(Instant.parse(jsonObject.getString("_updated_at"))) : null);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return univAdmin;
    }

    public static JSONObject postToJson(Post post) {

        JSONObject object = new JSONObject();
        try {

            object = post.getObjectID() != null ? object.put("_id", post.getObjectID()) : object;
            object = post.get_created_at() != null ? object.put("_created_at", post.get_created_at()) : object;
            object = post.get_updated_at() != null ? object.put("_updated_at", post.get_updated_at()) : object;

            object = post.getLikes() != null ? object.put("likes", post.getLikes()) : object;
            object = post.getShares() != null ? object.put("shares", post.getShares()) : object;
            object = post.getText() != null ? object.put("text", post.getText()) : object;

            object = post.getUserID() != null ? object.put("UserID", post.getUserID()) : object;

        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static JSONObject notificationToJson(Notification notification) {

        JSONObject object = new JSONObject();
        try {

            object = notification.getObjectID() != null ? object.put("_id", notification.getObjectID()) : object;
            object = notification.get_created_at() != null ? object.put("_created_at", notification.get_created_at()) : object;
            object = notification.get_updated_at() != null ? object.put("_updated_at", notification.get_updated_at()) : object;

            object = notification.getRead() != null ? object.put("read", notification.getRead()) : object;
            object = notification.getText() != null ? object.put("text", notification.getText()) : object;
            object = notification.getUserID() != null ? object.put("UserID", notification.getUserID()) : object;

        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static Notification jsonToNotification(JSONObject jsonObject) {

        Notification notification = new Notification();

        try {
            notification.setRead(jsonObject.has("read") ? jsonObject.getString("read") : null);
            notification.setText(jsonObject.has("text") ? jsonObject.getString("text") : null);
            notification.setUserID(jsonObject.has("UserID") ? jsonObject.getString("UserID") : null);

            notification.setObjectID(jsonObject.has("_id") ? jsonObject.getString("_id") : null);
            notification.set_created_at(jsonObject.has("_created_at") ? Date.from(Instant.parse(jsonObject.getString("_created_at"))) : null);
            notification.set_updated_at(jsonObject.has("_updated_at") ? Date.from(Instant.parse(jsonObject.getString("_updated_at"))) : null);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return notification;
    }

    public static JSONObject messageToJson(Message message) {

        JSONObject object = new JSONObject();
        try {

            object = message.getObjectID() != null ? object.put("_id", message.getObjectID()) : object;
            object = message.get_created_at() != null ? object.put("_created_at", message.get_created_at()) : object;
            object = message.get_updated_at() != null ? object.put("_updated_at", message.get_updated_at()) : object;

            object = message.getFrom() != null ? object.put("From", message.getFrom()) : object;
            object = message.getText() != null ? object.put("text", message.getText()) : object;
            object = message.getTo() != null ? object.put("To", message.getTo()) : object;

        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static Message jsonToMessage(JSONObject jsonObject) {

        Message message = new Message();

        try {
            message.setFrom(jsonObject.has("From") ? jsonObject.getString("From") : null);
            message.setText(jsonObject.has("text") ? jsonObject.getString("text") : null);
            message.setTo(jsonObject.has("To") ? jsonObject.getString("To") : null);

            message.setObjectID(jsonObject.has("_id") ? jsonObject.getString("_id") : null);
            message.set_created_at(jsonObject.has("_created_at") ? Date.from(Instant.parse(jsonObject.getString("_created_at"))) : null);
            message.set_updated_at(jsonObject.has("_updated_at") ? Date.from(Instant.parse(jsonObject.getString("_updated_at"))) : null);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return message;
    }

    public static Post jsonToPost(JSONObject jsonObject) {

        Post post = new Post();

        try {
            post.setPostID(jsonObject.has("PostID") ? jsonObject.getString("PostID") : null);
            post.setUserID(jsonObject.has("UserID") ? jsonObject.getString("UserID") : null);
            post.setText(jsonObject.has("text") ? jsonObject.getString("text") : null);
            post.setLikes(jsonObject.has("likes") ? jsonObject.getString("likes") : null);
            post.setShares(jsonObject.has("shares") ? jsonObject.getString("shares") : null);

            post.setObjectID(jsonObject.has("_id") ? jsonObject.getString("_id") : null);
            post.set_created_at(jsonObject.has("_created_at") ? Date.from(Instant.parse(jsonObject.getString("_created_at"))) : null);
            post.set_updated_at(jsonObject.has("_updated_at") ? Date.from(Instant.parse(jsonObject.getString("_updated_at"))) : null);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return post;
    }

    public static JSONObject courseToJson(Course student) {

        JSONObject object = new JSONObject();
        try {

            object = student.getCourseAdmin() != null ? object.put("CourseAdmin", student.getCourseAdmin()) : object;
            object = student.getCourseID() != null ? object.put("CourseID", student.getCourseID()) : object;
            object = student.getExpiring() != null ? object.put("Expiring", student.getExpiring()) : object;
            object = student.getLevel() != null ? object.put("Level", student.getLevel()) : object;
            object = student.getLevelInt() != null ? object.put("LevelInt", student.getLevelInt()) : object;
            object = student.getInfo() != null ? object.put("info", student.getInfo()) : object;
            object = student.getName() != null ? object.put("Name", student.getName()) : object;
            object = student.getStarting() != null ? object.put("Starting", student.getStarting()) : object;

            object = student.getObjectID() != null ? object.put("_id", student.getObjectID()) : object;
            object = student.get_created_at() != null ? object.put("_created_at", student.get_created_at()) : object;
            object = student.get_updated_at() != null ? object.put("_updated_at", student.get_updated_at()) : object;

            List<Document> universities = student.getUniversities();
            JSONArray array = new JSONArray();
            for (Document document : universities) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Universities", array) : object;

            List<Document> actionLogs = student.getActionLogs();
            array = new JSONArray();
            for (Document document : actionLogs) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("ActionLogs", array) : object;

            List<Document> batches = student.getBatches();
            array = new JSONArray();
            for (Document document : batches) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Batches", array) : object;

            Document status = student.getStatus();
            object = status != null && status.size() > 0 ? object.put("Status", new JSONObject(status.toJson())) : object;

            Document billing = student.getBilling();
            object = billing != null && billing.size() > 0 ? object.put("Billing", new JSONObject(billing.toJson())) : object;

            Document photo = student.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static JSONObject teacherToJson(Teacher student) {

        JSONObject object = new JSONObject();
        try {

            object = student.getUnivID() != null ? object.put("UnivID", student.getUnivID()) : object;
            object = student.getUEM_ID() != null ? object.put("UEM_ID", student.getUEM_ID()) : object;
            object = student.getUserID() != null ? object.put("UserID", student.getUserID()) : object;
            object = student.getInfo() != null ? object.put("info", student.getInfo()) : object;

            object = student.getObjectID() != null ? object.put("_id", student.getObjectID()) : object;
            object = student.get_created_at() != null ? object.put("_created_at", student.get_created_at()) : object;
            object = student.get_updated_at() != null ? object.put("_updated_at", student.get_updated_at()) : object;

            List<Document> documents = student.getDocuments();
            JSONArray array = new JSONArray();
            for (Document document : documents) {
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Documents", array) : object;

            Document photo = student.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static Teacher jsonToTeacher(JSONObject jsonObject) {

        Teacher univAdmin = new Teacher();

        try {
            univAdmin.setUnivID(jsonObject.has("UnivID") ? jsonObject.getString("UnivID") : null);
            univAdmin.setUEM_ID(jsonObject.has("UEM_ID") ? jsonObject.getString("UEM_ID") : null);
            univAdmin.setUserID(jsonObject.has("UserID") ? jsonObject.getString("UserID") : null);
            univAdmin.setInfo(jsonObject.has("info") ? jsonObject.getString("info") : null);

            List<Document> courses = new ArrayList<>();
            JSONArray array = jsonObject.has("Documents") ? jsonObject.getJSONArray("Documents") : new JSONArray();
            for (int i = 0; i < array.length(); i++) {
                courses.add(Document.parse(String.valueOf(array.get(i))));
            }
            univAdmin.setDocuments(courses);

            Document photo = new Document();
            JSONObject object = jsonObject.has("Photo") ? jsonObject.getJSONObject("Photo") : new JSONObject();
            photo = Document.parse(object.toString());
            univAdmin.setPhoto(photo);

            univAdmin.setObjectID(jsonObject.has("_id") ? jsonObject.getString("_id") : null);
            univAdmin.set_created_at(jsonObject.has("_created_at") ? Date.from(Instant.parse(jsonObject.getString("_created_at"))) : null);
            univAdmin.set_updated_at(jsonObject.has("_updated_at") ? Date.from(Instant.parse(jsonObject.getString("_updated_at"))) : null);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return univAdmin;
    }

    public static String getUTCStandardDateFormat() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String generateUniqueID() {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }

    public static Map<String, Object> retryCode(HttpClient client, HttpPost post, String url, List<NameValuePair> urlParameters, int retries) {

        int retry_count = 0;
        HttpResponse response = null;
        StringBuilder result = new StringBuilder();
        Exception exception = new Exception("DUMMY");

        boolean retryFlag = true;

        while ((response == null || retryFlag) && retry_count < retries) {
            try {
                if (response != null && response.getStatusLine().getStatusCode() >= 400) {
                    EntityUtils.consumeQuietly(response.getEntity());
                    Thread.sleep(1000);
                    logger.info("Retrying " + retry_count);
                }
                client = HttpClientBuilder.create().build();
                response = client.execute(post);
                logger.info("\nSending 'POST' request to URL : " + url);
                logger.info("Post parameters : " + post.getEntity());
                logger.info("POST Parameters : " + urlParameters.toString());
                logger.info("Response Code : " + response.getStatusLine().getStatusCode());

                if (response.getStatusLine().getStatusCode() >= 400) {
                    result = fetchResponseString(response);
                    logger.info("result" + result.toString());
                    retryFlag = true;
                } else {
                    result = fetchResponseString(response);
                    retryFlag = false;
                }
                retry_count++;
            } catch (Exception e) {
                retry_count++;
                retryFlag = true;
                exception = e;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", result.toString());
        map.put("status", String.valueOf(response.getStatusLine().getStatusCode()));
        map.put("exception", exceptionAsString(exception));
        return map;
    }

    public static String arrayToString(String[] array) {

        if (array.length > 0) {
            StringBuilder nameBuilder = new StringBuilder();

            for (String n : array) {
                nameBuilder.append("'").append(n.replace("'", "\\'")).append("',");
                // can also do the following
                // nameBuilder.append("'").append(n.replace("'", "''")).append("',");
            }

            nameBuilder.deleteCharAt(nameBuilder.length() - 1);

            return nameBuilder.toString();
        } else {
            return "";
        }
    }

    public static String dateFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'").format(date);
    }

    public static Date dateFormat(String date) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'").parse(date);
    }

    public static Date dateFormatShort(String date) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    public static String dateFormatShort(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String singleQuotesStringsByComma(String original) {

        String converted = "";
        if (original == null)
            return null;
        else if (original.length() == 0) {
            return "";
        } else if (!original.contains(",")) {
            return "'" + original + "'";
        } else {
            String[] ids = original.split(",");
            for (String id : ids) {
                converted =
                        converted != ""
                                ? converted + ",'" + id + "'"
                                : "'" + id + "'";
            }
        }
        return converted;
    }

    public static Map<String, Object> retryCode(HttpGet httpGet, int retries) {

        int retry_count = 0;
        HttpResponse response = null;
        StringBuilder result = new StringBuilder();
        Exception exception = new Exception("DUMMY");

        boolean retryFlag = true;

        while ((response == null || retryFlag) && retry_count < retries) {
            try {
                if (response != null && response.getStatusLine().getStatusCode() >= 400) {
                    EntityUtils.consumeQuietly(response.getEntity());
                    Thread.sleep(1000);
                    logger.info("Retrying " + retry_count);
                }
                DefaultHttpClient client = new DefaultHttpClient();
                logger.debug("client" + client.toString());
                logger.debug("httpGet" + httpGet.toString());
                response = client.execute(httpGet);
                logger.info("\nSending 'GET' request to URL : " + httpGet.getURI());
                logger.info("Response Code : " + response.getStatusLine().getStatusCode());

                if (response.getStatusLine().getStatusCode() >= 400) {
                    result = fetchResponseString(response);
                    logger.info("result" + result.toString());
                    JSONObject fb_response = new JSONObject(result.toString());
                    JSONObject error = fb_response.optJSONObject("error");
                    if (error != null && error.has("message")) {
                        retryFlag = true;
                    } else {
                        retryFlag = false;
                    }
                } else {
                    result = fetchResponseString(response);
                    retryFlag = false;
                }
                retry_count++;
            } catch (Exception e) {
                retryFlag = true;
                retry_count++;
                exception = e;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("result", result.toString());
        map.put("status", String.valueOf(response.getStatusLine().getStatusCode()));
        map.put("retry_count", String.valueOf(retry_count));
        map.put("exception", exceptionAsString(exception));
        return map;
    }

    public static String exceptionAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static StringBuilder fetchResponseString(HttpResponse response) {
        BufferedReader rd = null;
        StringBuilder result = new StringBuilder();
        if (response == null) {
            return result;
        }
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (IOException e) {
            logger.error(e);
        }
        return result;
    }

}