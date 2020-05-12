package com.uem.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MONGO_DbUtility {

    static Logger logger = LogUtil.getInstance();

    public static MongoClient mongo_client = null;
    public static MongoDatabase mongo_database = null;

    public static MongoCollection<Document> Test = null;

    public static MongoDatabase getDataBase() {

        if (null == mongo_client) {
            mongo_client = new MongoClient(new MongoClientURI("mongodb://admin:GuBlzZ98mdqugwpR5MPH6Eir@mongodb.back4app.com:27017/395cf4d3118743a4aa44c598d899689d?ssl=true"));
        }
        if (null == mongo_database) {
            mongo_database = mongo_client.getDatabase("395cf4d3118743a4aa44c598d899689d");
        }
        return mongo_database;
    }

    public static MongoCollection<Document> getTest() {
        if (null == Test) {
            Test = getDataBase().getCollection("Test");
            return Test;
        } else {
            return Test;
        }
    }

    public static List<Document> getReportRunMappings_InsightLevels(String insightLevelsComma, String statusCompleted, int days, String accountIds) {

        try {

            int hours = days * 24;

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -hours);
            Date oneHourBack = cal.getTime();

            DateTime dateStr = new org.joda.time.DateTime(oneHourBack.getTime(), org.joda.time.DateTimeZone.UTC);

            MongoCollection<Document> collection = MONGO_DbUtility.getTest();
            BsonDocument filter = null;
            filter = BsonDocument
                    .parse("{ " +
                            "accountID:{$in:[" + accountIds + "]}," +
                            "insightsLevel:{$in:[" + insightLevelsComma + "]}," +
                            "_updated_at : { $gt : ISODate(\"" + dateStr.toString() + "\")}," +
                            "statusCompleted:{$eq:'" + statusCompleted + "'}" +
                            "}");

            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getReportRunMappings_InsightLevels \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getReportRunMappings_InsightLevels", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }


}