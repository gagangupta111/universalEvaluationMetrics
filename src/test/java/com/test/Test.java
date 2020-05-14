package com.test;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.uem.util.AllDBOperations;
import com.uem.google.bigquery.main.BQOperationsTestTable;
import com.uem.model.TestClass;
import com.uem.util.GAuthenticate;
import com.uem.util.MongoDBUtil;
import com.uem.util.ParseUtil;
import org.bson.BsonDocument;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {

        List<Document> list = testGetUsers("gagan");
        System.out.println(list);
    }

    public static void testBatchUpdate() throws Exception{

        JSONObject body = new JSONObject();
        body.put("info", "TEST");

        Map<String, JSONObject> map = new HashMap<>();
        map.put("PXHWkQLVHa", body);
        map.put("F7Icx1marA", body);
        map.put("IiGYm68cKx", body);

        ParseUtil.batchUpdateInParseTable(map, "Batch");

    }

    public static List<Document> testGetUsers(String email){

        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "Email:{$regex:/" + email +"/}" +
                        "}");
        return MongoDBUtil.getAllUniversalUsers(filter);

    }

    public static List<Document> testGetTest(){

        String info = "info";
        BsonDocument filter = BsonDocument
                .parse("{ " +
                        "col4:{$regex:/" + info +"/}" +
                        "}");

        return MongoDBUtil.getAllTestObjects(filter);

    }

    public static void getAllTestObjects(){
        List<Document> documents = MongoDBUtil.getAllTestObjects(BsonDocument
                .parse("{ " + "}"));
        System.out.println(documents);
    }


    public static void deleteAllTestObjects(){

        List<Document> documents = MongoDBUtil.getAllTestObjects(BsonDocument
                .parse("{ " + "}"));

        List<String> objectIDs = new ArrayList<>();
        for (Document document : documents){
            objectIDs.add(document.getString("_id"));
        }

        ParseUtil.batchDeleteAllInParseTable(objectIDs, "TEST");
    }

    public static void getParseTest(String objectID) throws Exception {

        TestClass testClass = ParseUtil.getParseTest(objectID);
        System.out.println(testClass);
        testClass.setCol1("TEST");
        testClass.setCol2(new JSONArray());
        testClass.setCol3(new JSONObject());

        ParseUtil.updateStatusInParse(testClass);

    }

    public static void batchSaveInParseTest() throws Exception {

        TestClass testClass = new TestClass();
        testClass.setCol1("col1TestValue1");

        Map<String, Object> map = new HashMap<>();
        map.put("map", 72652L);

        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key3", "value");
        jsonObject.put("key4", 15414);
        jsonObject.put("key5", 1541427837828L);
        jsonObject.put("key6", new JSONObject(map));

        array.put(jsonObject);
        array.put("one");
        array.put(1233);
        array.put(7657125L);
        array.put(872638.2876386);

        testClass.setCol2(array);
        testClass.setCol3(jsonObject);
        Boolean aBoolean = ParseUtil.saveInParseTest(testClass);
        System.out.println(aBoolean);
    }

    public static void saveInParseTest() throws Exception {

        TestClass testClass = new TestClass();
        testClass.setCol1("col1TestValue1");

        Map<String, Object> map = new HashMap<>();
        map.put("map", 72652L);

        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key3", "value");
        jsonObject.put("key4", 15414);
        jsonObject.put("key5", 1541427837828L);
        jsonObject.put("key6", new JSONObject(map));

        array.put(jsonObject);
        array.put("one");
        array.put(1233);
        array.put(7657125L);
        array.put(872638.2876386);

        testClass.setCol2(array);
        testClass.setCol3(jsonObject);
        Boolean aBoolean = ParseUtil.saveInParseTest(testClass);
        System.out.println(aBoolean);
    }

    public static void reCreateWholeStructure() throws IOException {

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);
        AllDBOperations.reCreateWholeStructure(bigquery);

    }

    public static void test1() throws Exception {

        List<TableDataInsertAllRequest.Rows> partition = new ArrayList<>();

        TableDataInsertAllRequest.Rows row = new TableDataInsertAllRequest.Rows();
        HashMap<String, Object> report = new HashMap<String, Object>();
        report.put("account_id", "test_account1");
        report.put("name", "test_account");
        row.setJson(report);
        partition.add(row);

        TableDataInsertAllRequest.Rows row2 = new TableDataInsertAllRequest.Rows();
        HashMap<String, Object> report2 = new HashMap<String, Object>();
        report2.put("account_id", "test_account1");
        report2.put("name", "test_account");
        row2.setJson(report2);
        partition.add(row2);

        TableDataInsertAllRequest.Rows row3 = new TableDataInsertAllRequest.Rows();
        HashMap<String, Object> report3 = new HashMap<String, Object>();
        report3.put("account_id", "test_account1");
        row3.setJson(report3);
        partition.add(row3);

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);
        BQOperationsTestTable.StructureValidate(bigquery);

        Map<String, Object> objectMap = BQOperationsTestTable.insertDataRowsWithStats(bigquery, partition);
        System.out.println(objectMap);
    }

}
