package com.test;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.uem.google.bigquery.main.AllBQOperations;
import com.uem.google.bigquery.main.BQOperationsTestTable;
import com.uem.util.GAuthenticate;

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

        Bigquery bigquery = GAuthenticate.getAuthenticated();
        AllBQOperations.StructureValidate(bigquery);

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

        Bigquery bigquery = GAuthenticate.getAuthenticated();
        BQOperationsTestTable.StructureValidate(bigquery);

        Map<String, Object> objectMap = BQOperationsTestTable.insertDataRowsWithStats(bigquery, partition);
        System.out.println(objectMap);
    }

}
