package com.test;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.uem.google.bigquery.main.AllBQOperations;
import com.uem.google.bigquery.main.BQOperationsTestTable;
import com.uem.google.bigquery.main.BQTable_UnivAdmin;
import com.uem.util.GAuthenticate;
import com.uem.util.UtilsManager;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception{

        test2();

    }

    public static void test2() throws IOException {

        Bigquery bigquery = GAuthenticate.getAuthenticated(true);
        AllBQOperations.reCreateWholeStructure(bigquery);

    }

    public static  Map<String, Object> createTestUnivUser(){
        Bigquery bigquery = GAuthenticate.getAuthenticated(true);

        String UserID = UtilsManager.generateUniqueID();
        String UEM_ID = UserID;
        String UnivID = UserID;

        List<Document> array = new ArrayList<>();
        Document document = new Document();
        document.put("Course_Name", "Course_Name");
        document.put("Course_Details", "Course_Details");
        List<Object> bytes = new ArrayList<>();
        bytes.add("ahjashjhakk");
        bytes.add("skjhdkhdkjhkah");
        document.put("Attachments", bytes);
        array.add(document);

        ArrayList<TableDataInsertAllRequest.Rows> datachunk =
                new ArrayList<TableDataInsertAllRequest.Rows>();
        TableDataInsertAllRequest.Rows row = new TableDataInsertAllRequest.Rows();
        Map<String, Object> data = new HashMap<>();
        data.put("UserID", UserID);
        data.put("UEM_ID", UEM_ID);
        data.put("UnivID", UnivID);
        data.put("Documents", array);

        row.setJson(data);
        datachunk.add(row);
        Boolean aBoolean = BQTable_UnivAdmin.insertDataRows(bigquery, datachunk);
        return data;
    }

    public static void test1() throws Exception{

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
