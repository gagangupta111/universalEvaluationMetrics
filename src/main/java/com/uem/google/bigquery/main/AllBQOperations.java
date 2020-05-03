package com.uem.google.bigquery.main;

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

public class AllBQOperations {

    private static final String PROJECT_ID = "universalevaluationmetrics";
    private static final String DATASET_ID = "universalEvaluationMetrics";

    public static String toString_() {
        return "AllBQOperations{PROJECT_ID:" + PROJECT_ID + ", DATASET_ID:" + DATASET_ID + "}";
    }

    static Logger logger = LogUtil.getInstance();

    public static Boolean StructureValidate(Bigquery bigquery) {

        try {

            if (createDataset(bigquery)) {

                if (createAllTable(bigquery)) {

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

    public static Boolean createAllTable(Bigquery bigquery) {

        BQTable_Batch.createTable(bigquery, true, true);
        BQTable_Course.createTable(bigquery, true, true);
        BQTable_CourseAdmin.createTable(bigquery, true, true);
        BQTable_Permissions.createTable(bigquery, true, true);
        BQTable_Student.createTable(bigquery, true, true);
        BQTable_Teacher.createTable(bigquery, true, true);
        BQTable_UnivAdmin.createTable(bigquery, true, true);
        BQTable_University.createTable(bigquery, true, true);
        BQTableUser.createTable(bigquery, true, true);
        return true;
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


}