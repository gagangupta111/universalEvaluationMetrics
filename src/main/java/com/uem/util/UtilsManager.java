package com.uem.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class UtilsManager {

    static Logger logger = LogUtil.getInstance();

    public static void multipartFileToFile(MultipartFile file, Path dir) throws  Exception{
        Path filepath = Paths.get(dir.toString(), file.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
        }
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