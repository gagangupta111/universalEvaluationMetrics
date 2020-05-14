package com.uem.util;

import com.uem.model.TestClass;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseUtil {

    public static final String PARSE_URL = "https://parseapi.back4app.com";
    public static final String PARSE_APPLICATION_ID = "6viCuXhfL9rOaJr6RU3AbGwqxDut7h42WQqS164g";
    public static final String PARSE_REST_API_KEY = "mhGiQXRJgERYXiRSmCKI6I34ctW93tWWBv1DLBCs";

    private static Logger logger = LogUtil.getInstance();

    public static Map<String, Object> deleteAllObjectsAllTables() {

        BsonDocument filter = BsonDocument
                .parse("{ " + "}");
        List<Document> documentList = MongoDBCollections.getAllBatch(filter);
        List<String> objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "Batch");
        }

        // Course

        documentList = MongoDBCollections.getAllCourse(filter);
        objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "Course");
        }

        // getAllCourseAdmin

        documentList = MongoDBCollections.getAllCourseAdmin(filter);
        objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "CourseAdmin");
        }

        // getAllCourseAdmin

        documentList = MongoDBCollections.getAllPermissions(filter);
        objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "Permissions");
        }

        // getAllCourseAdmin

        documentList = MongoDBCollections.getAllStudents(filter);
        objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "Student");
        }

        // getAllCourseAdmin

        documentList = MongoDBCollections.getAllTeachers(filter);
        objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "Teacher");
        }

        // getAllCourseAdmin

        documentList = MongoDBCollections.getAllUniversalUsers(filter);
        objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "UniversalUser");
        }

        // getAllCourseAdmin

        documentList = MongoDBCollections.getAllUniversity(filter);
        objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "University");
        }

        // getAllCourseAdmin

        documentList = MongoDBCollections.getAllUniversityAdmin(filter);
        objectIDs = new ArrayList<>();

        for (Document document : documentList) {
            objectIDs.add(document.getString("_id"));
        }

        if (objectIDs.size() > 0) {
            ParseUtil.batchDeleteAllInParseTable(objectIDs, "UnivAdmin");
        }

        return null;
    }

    public static Map<String, Object> createDummyObjectsAllTables() {

        try {

            JSONObject body = new JSONObject();
            body.put("info", "TEST");

            ParseUtil.batchCreateInParseTable(body, "Batch");
            ParseUtil.batchCreateInParseTable(body, "Course");
            ParseUtil.batchCreateInParseTable(body, "CourseAdmin");
            ParseUtil.batchCreateInParseTable(body, "Permissions");
            ParseUtil.batchCreateInParseTable(body, "Student");
            ParseUtil.batchCreateInParseTable(body, "Teacher");
            ParseUtil.batchCreateInParseTable(body, "UnivAdmin");
            ParseUtil.batchCreateInParseTable(body, "UniversalUser");
            ParseUtil.batchCreateInParseTable(body, "University");

        } catch (Exception e) {

        }

        return null;
    }

    public static Map<String, Object>  batchDeleteAllInParseTable(List<String> objectIDs, String className) {

        Exception exception = new Exception("DUMMY");
        Map<String, Object> res = new HashMap<>();
        res.put("className", className);
        res.put("objectIDs", String.join(",", objectIDs));

        try {
            String updateURL = PARSE_URL + "/batch";

            HttpClient clientUpdate = new DefaultHttpClient();
            HttpPost post = new HttpPost(updateURL);

            post.setHeader("X-Parse-Application-Id", PARSE_APPLICATION_ID);
            post.setHeader("X-Parse-REST-API-Key", PARSE_REST_API_KEY);

            JSONArray array = new JSONArray();

            for (String objectID : objectIDs){

                JSONObject request = new JSONObject();
                request.put("method", "DELETE");
                request.put("path", "/classes/" + className + "/" + objectID);

                array.put(request);

            }

            JSONObject payload = new JSONObject();
            payload.put("requests", array);

            StringEntity requestEntity = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);
            post.setEntity(requestEntity);

            HttpResponse httpResponse = clientUpdate.execute(post);

            if (httpResponse == null || httpResponse.getStatusLine() == null || httpResponse.getEntity() == null) {
                RollbarManager.sendExceptionOnRollBar("batchDeleteAllInParseTable", String.valueOf(httpResponse));
            }

            int status = Integer.parseInt(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            StringBuilder response = UtilsManager.fetchResponseString(httpResponse);
            res.put("response", response);
            res.put("status", status);

        } catch (Exception e) {
            exception = e;
            RollbarManager.sendExceptionOnRollBar("batchDeleteAllInParseTable", e);
            res.put("exception", UtilsManager.exceptionAsString(exception));
            logger.debug(res);
        }

        return res;

    }

    public static Map<String, Object> batchCreateInParseTable(JSONObject body, String className) {

        Exception exception = new Exception("DUMMY");
        Map<String, Object> res = new HashMap<>();
        res.put("className", className);
        res.put("body", body);

        try {

            String updateURL = PARSE_URL + "/batch";

            HttpClient clientUpdate = new DefaultHttpClient();
            HttpPost post = new HttpPost(updateURL);

            post.setHeader("X-Parse-Application-Id", PARSE_APPLICATION_ID);
            post.setHeader("X-Parse-REST-API-Key", PARSE_REST_API_KEY);

            JSONObject request = new JSONObject();
            request.put("method", "POST");
            request.put("path", "/classes/" + className);
            request.put("body", body);

            JSONArray array = new JSONArray();
            array.put(request);

            JSONObject payload = new JSONObject();
            payload.put("requests", array);

            StringEntity requestEntity = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);
            post.setEntity(requestEntity);

            HttpResponse httpResponse = clientUpdate.execute(post);

            if (httpResponse == null || httpResponse.getStatusLine() == null || httpResponse.getEntity() == null) {
                RollbarManager.sendExceptionOnRollBar("batchSaveInParseTable", String.valueOf(httpResponse));
            }

            int status = Integer.parseInt(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            StringBuilder response = UtilsManager.fetchResponseString(httpResponse);
            res.put("response", response);
            res.put("status", status);

        } catch (Exception e) {
            exception = e;
            RollbarManager.sendExceptionOnRollBar("batchSaveInParseTable", e);
            res.put("exception", UtilsManager.exceptionAsString(exception));
            logger.debug(res);
        }

        return res;
    }

    public static Map<String, Object> batchUpdateInParseTable(Map<String, JSONObject> map, String className) {

        Exception exception = new Exception("DUMMY");
        Map<String, Object> res = new HashMap<>();
        res.put("map", map);

        try {
            String updateURL = PARSE_URL + "/batch";

            HttpClient clientUpdate = new DefaultHttpClient();
            HttpPost post = new HttpPost(updateURL);

            post.setHeader("X-Parse-Application-Id", PARSE_APPLICATION_ID);
            post.setHeader("X-Parse-REST-API-Key", PARSE_REST_API_KEY);

            JSONArray array = new JSONArray();

            for (String objectID : map.keySet()){

                JSONObject request = new JSONObject();
                request.put("method", "PUT");
                request.put("path", "/classes/" + className + "/" + objectID);
                request.put("body", map.get(objectID));

                array.put(request);

            }

            JSONObject payload = new JSONObject();
            payload.put("requests", array);

            StringEntity requestEntity = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);
            post.setEntity(requestEntity);

            HttpResponse httpResponse = clientUpdate.execute(post);

            if (httpResponse == null || httpResponse.getStatusLine() == null || httpResponse.getEntity() == null) {
                RollbarManager.sendExceptionOnRollBar("batchUpdateInParseTable", String.valueOf(httpResponse));
            }

            int status = Integer.parseInt(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            StringBuilder response = UtilsManager.fetchResponseString(httpResponse);
            res.put("response", response);
            res.put("status", status);

        } catch (Exception e) {
            exception = e;
            RollbarManager.sendExceptionOnRollBar("batchUpdateInParseTable", e);
            res.put("exception", UtilsManager.exceptionAsString(exception));
            logger.debug(res);
        }

        return res;

    }

    public static String updateStatusInParse(TestClass testClass) {

        try {

            Map<String, Object> res = new HashMap<>();
            res.put("mapping", testClass.getObjectID());

            String updateURL = PARSE_URL + "/classes/" + "TEST" + "/" + testClass.getObjectID();

            HttpClient clientUpdate = new DefaultHttpClient();
            HttpPut post = new HttpPut(updateURL);

            post.setHeader("X-Parse-Application-Id", PARSE_APPLICATION_ID);
            post.setHeader("X-Parse-REST-API-Key", PARSE_REST_API_KEY);

            JSONObject payload = new JSONObject();
            payload.put("col1", testClass.getCol1());
            payload.put("col2", testClass.getCol2());
            payload.put("col3", testClass.getCol3());

            StringEntity requestEntity = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);
            post.setEntity(requestEntity);

            HttpResponse httpResponse = clientUpdate.execute(post);

            res.put("response", String.valueOf(httpResponse));
            if (httpResponse == null || httpResponse.getStatusLine() == null || httpResponse.getEntity() == null) {
                RollbarManager.sendExceptionOnRollBar("UPDATE_ROLL_BAR_STATUS", res);
                return null;
            }

            int status = Integer.parseInt(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            StringBuilder buffer1 = UtilsManager.fetchResponseString(httpResponse);

            if (status >= 200 && status < 300) {
                return buffer1.toString();
            } else {
                RollbarManager.sendExceptionOnRollBar("UPDATE_ROLL_BAR_STATUS", res);
                return null;
            }

        } catch (Exception e) {
            RollbarManager.sendExceptionOnRollBar("UPDATE_ROLL_BAR_STATUS", e);
            return null;
        }
    }

    public static boolean saveInParseTest(TestClass testClass) {

        try {

            String updateURL = PARSE_URL + "/classes/" + "TEST";

            HttpClient clientUpdate = new DefaultHttpClient();
            HttpPost post = new HttpPost(updateURL);

            post.setHeader("X-Parse-Application-Id", PARSE_APPLICATION_ID);
            post.setHeader("X-Parse-REST-API-Key", PARSE_REST_API_KEY);

            JSONObject payload = new JSONObject();
            payload.put("col1", testClass.getCol1());
            payload.put("col2", testClass.getCol2());
            payload.put("col3", testClass.getCol3());

            StringEntity requestEntity = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);
            post.setEntity(requestEntity);

            HttpResponse httpResponse = clientUpdate.execute(post);

            if (httpResponse == null || httpResponse.getStatusLine() == null || httpResponse.getEntity() == null) {
                RollbarManager.sendExceptionOnRollBar("SAVE_IN_PARSE_NEW_REPORT_RUN_ID", String.valueOf(httpResponse));
            }

            int status = Integer.parseInt(String.valueOf(httpResponse.getStatusLine().getStatusCode()));

            StringBuffer buffer1 = new StringBuffer();
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line1 = "";
            while ((line1 = reader1.readLine()) != null) {
                buffer1.append(line1);
            }

            Map<String, Object> res = new HashMap<>();
            res.put("result", reader1.toString());
            res.put("mapping", testClass.toString());

            if (status >= 200 && status < 300) {
                return true;
            } else {
                RollbarManager.sendExceptionOnRollBar("SAVE_IN_PARSE_NEW_REPORT_RUN_ID", res);
                return false;
            }

        } catch (Exception e) {
            RollbarManager.sendExceptionOnRollBar("SAVE_IN_PARSE_NEW_REPORT_RUN_ID", e);
            return false;
        }
    }

    public static TestClass getParseTest(String objectID) {

        try {

            String updateURL = PARSE_URL + "/classes/" + "TEST" + "/" + objectID;

            HttpClient clientUpdate = new DefaultHttpClient();
            HttpGet post = new HttpGet(updateURL);

            post.setHeader("X-Parse-Application-Id", PARSE_APPLICATION_ID);
            post.setHeader("X-Parse-REST-API-Key", PARSE_REST_API_KEY);

            HttpResponse httpResponse = clientUpdate.execute(post);

            if (httpResponse == null || httpResponse.getStatusLine() == null || httpResponse.getEntity() == null) {
                RollbarManager.sendExceptionOnRollBar("SAVE_IN_PARSE_NEW_REPORT_RUN_ID", String.valueOf(httpResponse));
            }

            int status = Integer.parseInt(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            StringBuilder response = UtilsManager.fetchResponseString(httpResponse);

            Map<String, Object> res = new HashMap<>();
            res.put("result", response.toString());

            if (status >= 200 && status < 300) {
                TestClass testClass = new TestClass();
                JSONObject jsonObject = new JSONObject(response.toString());
                testClass.setObjectID(jsonObject.getString("objectId"));
                testClass.setCol1(jsonObject.getString("col1"));
                testClass.setCol2((jsonObject.getJSONArray("col2")));
                testClass.setCol3((jsonObject.getJSONObject("col3")));

                return testClass;
            } else {
                RollbarManager.sendExceptionOnRollBar("SAVE_IN_PARSE_NEW_REPORT_RUN_ID", res);
                return null;
            }

        } catch (Exception e) {
            RollbarManager.sendExceptionOnRollBar("SAVE_IN_PARSE_NEW_REPORT_RUN_ID", e);
            return null;
        }
    }

}
