package com.uem.archieve.main;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.*;
import com.google.api.services.bigquery.model.DatasetList.Datasets;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows;
import com.google.api.services.bigquery.model.TableList.Tables;
import com.uem.util.LogUtil;
import com.uem.util.UtilsManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BQOperationsTestTable {

    private static final String PROJECT_ID = "universalevaluationmetrics";
    private static final String DATASET_ID = "universalEvaluationMetrics";
    private static final String TABLE_ID = "test_table";

    public static String toString_() {
        return "BQOperations{PROJECT_ID:" + PROJECT_ID + ", DATASET_ID:" + DATASET_ID + ", TABLE_ID" + TABLE_ID + "}";
    }

    static Logger logger = LogUtil.getInstance();

    public static Boolean StructureValidate(Bigquery bigquery) {

        try {

            if (createDataset(bigquery)) {

                if (createTable(bigquery)) {

                    return true;

                } else {
                    logger.debug("Response Message : Couldn't create Table. Returning Back with FALSE status.");
                    return false;
                }

            } else {
                logger.debug("Response Message : Couldn't find Dataset. Returning Back with FALSE status.");
                return false;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;

        }

    }

    public static Boolean createDataset(Bigquery bigquery) {

        try {

            DatasetList datasets = bigquery.datasets().list(PROJECT_ID).setMaxResults(1000L).execute();

            Boolean datasetCreate = true;

            for (Datasets dataset : datasets.getDatasets()) {

                if (dataset.getId().equalsIgnoreCase(PROJECT_ID + ":" + DATASET_ID)) {

                    datasetCreate = false;

                }

            }

            if (datasetCreate) {
                Dataset datasetresponse = bigquery.datasets().insert(PROJECT_ID, new Dataset().setId(DATASET_ID).setKind("bigquery#dataset").setDatasetReference(new DatasetReference().setProjectId(PROJECT_ID).setDatasetId(DATASET_ID))).execute();
                logger.debug(datasetresponse);
                return true;
            } else {
                logger.debug("Response Message : Dataset is already created in Big Query.");
                return true;
            }

        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

    public static Boolean createTable(Bigquery bigquery) {

        try {

            TableList tables = bigquery.tables().list(PROJECT_ID, DATASET_ID).setMaxResults(1000L).execute();

            logger.debug(tables);

            Boolean deleteTable = false;
            Boolean createTable = true;

            if (null != tables && null != tables.getTables()) {

                logger.debug("Inside IF.");

                for (Tables table : tables.getTables()) {

                    if (table.getId().equalsIgnoreCase(PROJECT_ID + ":" + DATASET_ID + "." + TABLE_ID)) {
                        deleteTable = false;
                        createTable = false;
                        break;
                    }

                }

                if (deleteTable) {
                    bigquery.tables().delete(PROJECT_ID, DATASET_ID, TABLE_ID).execute();
                }

            }

            if (createTable) {

                ArrayList<TableFieldSchema> fields = new ArrayList<TableFieldSchema>();

                fields.add(new TableFieldSchema().setName("account_id").setType("STRING"));
                fields.add(new TableFieldSchema().setName("name").setType("STRING"));
                fields.add(new TableFieldSchema().setName("user").setType("STRING"));
                fields.add(new TableFieldSchema().setName("pass").setType("STRING"));

                Table content = new Table();
                content.setSchema(new TableSchema().setFields(fields));
                content.setId(TABLE_ID);
                content.setKind("bigquery#table");
                content.setTableReference(new TableReference().setProjectId(PROJECT_ID).setDatasetId(DATASET_ID).setTableId(TABLE_ID));

                Table table = bigquery.tables().insert(PROJECT_ID, DATASET_ID, content).execute();
                logger.debug(table);

            }
            return true;

        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

    public static Map<String, Object> insertDataRowsWithStats(Bigquery bigquery, List<Rows> datachunk) {

        Map<String, Object> insertResponse = new HashMap<>();
        insertResponse.put("success", Boolean.FALSE);
        insertResponse.put("fbRowCount", 0);
        insertResponse.put("bqRowCount", 0);

        int actualCount = datachunk.size();
        int failedCount = 0;
        try {

            logger.debug(datachunk.toString());
            TableDataInsertAllRequest content = new TableDataInsertAllRequest();
            content.setKind("bigquery#tableDataInsertAllRequest");
            content.setRows(datachunk);

            TableDataInsertAllResponse response = bigquery.tabledata().insertAll(PROJECT_ID, DATASET_ID, TABLE_ID, content).execute();
            List<TableDataInsertAllResponse.InsertErrors> insertErrors = response.getInsertErrors();
            logger.debug(response.toPrettyString());
            if (insertErrors != null && !insertErrors.isEmpty()) {
                failedCount = insertErrors.size();
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            failedCount = datachunk.size();
        }

        insertResponse.put("success", failedCount > 0 ? Boolean.FALSE : Boolean.TRUE);
        insertResponse.put("fbRowCount", actualCount);
        insertResponse.put("bqRowCount", actualCount - failedCount);
        return insertResponse;
    }

    public static Boolean insertDataRows(Bigquery bigquery, List<Rows> datachunk) {

        try {

            for (Rows row : datachunk) {
                logger.debug(row.getJson().toString());
            }

            logger.debug(bigquery);

            TableDataInsertAllRequest content = new TableDataInsertAllRequest();
            content.setKind("bigquery#tableDataInsertAllRequest");
            content.setRows(datachunk);

            TableDataInsertAllResponse response = bigquery.tabledata().insertAll(PROJECT_ID, DATASET_ID, TABLE_ID, content).execute();
            List<TableDataInsertAllResponse.InsertErrors> insertErrors = response.getInsertErrors();
            StringBuilder errorData = new StringBuilder();
            int count = 0;
            if (insertErrors != null && !insertErrors.isEmpty()) {
                for (TableDataInsertAllResponse.InsertErrors errors : insertErrors) {
                    if (count > 10) {
                        break;
                    }
                    errorData.append(errors.toPrettyString());
                    count++;
                }
            }
            logger.debug(response.toPrettyString());
            if (errorData.length() > 0) {
            }

            return true;

        } catch (Exception e) {

            logger.error(e);
            return false;

        }

    }

}