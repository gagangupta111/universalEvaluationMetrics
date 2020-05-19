package com.uem.util;

import com.uem.model.Student;
import com.uem.model.Teacher;
import com.uem.model.UnivAdmin;
import com.uem.model.University;
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

    public static void multipartFileToFile(MultipartFile file, Path dir) throws  Exception{
        Path filepath = Paths.get(dir.toString(), file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }
    }

    public static University jsonToUniversity(JSONObject jsonObject){

        University university = new University();
        try {
            university.setUnivID(jsonObject.has("UnivID") ? jsonObject.getString("UnivID") : null);
            university.setName(jsonObject.has("Name") ? jsonObject.getString("Name") : null);
            university.setStarted(jsonObject.has("Started") ? jsonObject.getString("Started") : null);

            List<String> univAdmins = new ArrayList<>();
            JSONArray array = jsonObject.has("UnivAdmins") ? jsonObject.getJSONArray("UnivAdmins") : new JSONArray();
            for (int i = 0; i < array.length(); i++){
                univAdmins.add(String.valueOf(array.get(i)));
            }
            university.setUnivAdmins(univAdmins);

            List<Document> students = new ArrayList<>();
            array = jsonObject.has("Students") ? jsonObject.getJSONArray("Students") : new JSONArray();
            for (int i = 0; i < array.length(); i++){
                students.add(Document.parse(String.valueOf(array.get(i))));
            }
            university.setStudents(students);

            List<Document> teachers = new ArrayList<>();
            array = jsonObject.has("Teachers") ? jsonObject.getJSONArray("Teachers") : new JSONArray();
            for (int i = 0; i < array.length(); i++){
                teachers.add(Document.parse(String.valueOf(array.get(i))));
            }
            university.setTeachers(teachers);

            List<Document> courses = new ArrayList<>();
            array = jsonObject.has("Courses") ? jsonObject.getJSONArray("Courses") : new JSONArray();
            for (int i = 0; i < array.length(); i++){
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
            for (int i = 0; i < array.length(); i++){
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
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return university;
    }

    public static JSONObject universityToJson(University university){

        JSONObject object = new JSONObject();
        try {
            object = university.getUnivID() != null ? object.put("UnivID", university.getUnivID()) : object;
            object = university.getName() != null ? object.put("Name", university.getName()) : object;
            object = university.getStarted() != null ? object.put("Started", university.getStarted()) : object;

            List<String> univAdmins = university.getUnivAdmins();
            JSONArray array = new JSONArray();
            for (String univAdmin : univAdmins){
                array.put(univAdmin);
            }
            object = array.length() > 0 ? object.put("UnivAdmins", array) : object;

            List<Document> students = university.getStudents();
            array = new JSONArray();
            for (Document document : students){
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Students", array) : object;

            List<Document> teachers = university.getTeachers();
            array = new JSONArray();
            for (Document document : teachers){
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Teachers", array) : object;

            List<Document> courses = university.getCourses();
            array = new JSONArray();
            for (Document document : courses){
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
            for (Document document : actionLogs){
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

        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static JSONObject adminToJson(UnivAdmin univAdmin){

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
            for (Document document : documents){
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Documents", array) : object;

            Document photo = univAdmin.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static UnivAdmin jsonToAdmin(JSONObject jsonObject){

        UnivAdmin univAdmin = new UnivAdmin();

        try {
            univAdmin.setUnivID(jsonObject.has("UnivID") ? jsonObject.getString("UnivID") : null);
            univAdmin.setUEM_ID(jsonObject.has("UEM_ID") ? jsonObject.getString("UEM_ID") : null);
            univAdmin.setUserID(jsonObject.has("UserID") ? jsonObject.getString("UserID") : null);
            univAdmin.setInfo(jsonObject.has("info") ? jsonObject.getString("info") : null);

            List<Document> courses = new ArrayList<>();
            JSONArray array = jsonObject.has("Documents") ? jsonObject.getJSONArray("Documents") : new JSONArray();
            for (int i = 0; i < array.length(); i++){
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
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return univAdmin;
    }

    public static JSONObject studentToJson(Student student){

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
            for (Document document : documents){
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Documents", array) : object;

            Document photo = student.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static Student jsonToStudent(JSONObject jsonObject){

        Student univAdmin = new Student();

        try {
            univAdmin.setUnivID(jsonObject.has("UnivID") ? jsonObject.getString("UnivID") : null);
            univAdmin.setUEM_ID(jsonObject.has("UEM_ID") ? jsonObject.getString("UEM_ID") : null);
            univAdmin.setUserID(jsonObject.has("UserID") ? jsonObject.getString("UserID") : null);
            univAdmin.setInfo(jsonObject.has("info") ? jsonObject.getString("info") : null);

            List<Document> courses = new ArrayList<>();
            JSONArray array = jsonObject.has("Documents") ? jsonObject.getJSONArray("Documents") : new JSONArray();
            for (int i = 0; i < array.length(); i++){
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
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return univAdmin;
    }

    public static JSONObject teacherToJson(Teacher student){

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
            for (Document document : documents){
                array.put(new JSONObject(document.toJson()));
            }
            object = array.length() > 0 ? object.put("Documents", array) : object;

            Document photo = student.getPhoto();
            object = photo != null && photo.size() > 0 ? object.put("Photo", new JSONObject(photo.toJson())) : object;


        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return object;
    }

    public static Teacher jsonToTeacher(JSONObject jsonObject){

        Teacher univAdmin = new Teacher();

        try {
            univAdmin.setUnivID(jsonObject.has("UnivID") ? jsonObject.getString("UnivID") : null);
            univAdmin.setUEM_ID(jsonObject.has("UEM_ID") ? jsonObject.getString("UEM_ID") : null);
            univAdmin.setUserID(jsonObject.has("UserID") ? jsonObject.getString("UserID") : null);
            univAdmin.setInfo(jsonObject.has("info") ? jsonObject.getString("info") : null);

            List<Document> courses = new ArrayList<>();
            JSONArray array = jsonObject.has("Documents") ? jsonObject.getJSONArray("Documents") : new JSONArray();
            for (int i = 0; i < array.length(); i++){
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
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
        }
        return univAdmin;
    }

    public static String getUTCStandardDateFormat(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String generateUniqueID(){
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

    public static String arrayToString(String[] array){

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

    public static String singleQuotesStringsByComma(String original){

        String converted = "";
        if (original == null)
            return null;
        else if (original.length() == 0){
            return "";
        }else if (!original.contains(",")){
            return "'" + original + "'";
        }else {
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