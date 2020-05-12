package com.uem.util;

import com.uem.model.TestClass;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ParseUtil {

    public static final String PARSE_URL = "https://parseapi.back4app.com";
    public static final String PARSE_APPLICATION_ID = "6viCuXhfL9rOaJr6RU3AbGwqxDut7h42WQqS164g";
    public static final String PARSE_REST_API_KEY = "mhGiQXRJgERYXiRSmCKI6I34ctW93tWWBv1DLBCs";

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

}
