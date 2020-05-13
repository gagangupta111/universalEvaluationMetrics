package com.uem.util;

import org.bson.BsonDocument;
import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoDBUtil {

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

            ParseUtil.batchSaveInParseTable(body, "Batch");
            ParseUtil.batchSaveInParseTable(body, "Course");
            ParseUtil.batchSaveInParseTable(body, "CourseAdmin");
            ParseUtil.batchSaveInParseTable(body, "Permissions");
            ParseUtil.batchSaveInParseTable(body, "Student");
            ParseUtil.batchSaveInParseTable(body, "Teacher");
            ParseUtil.batchSaveInParseTable(body, "UnivAdmin");
            ParseUtil.batchSaveInParseTable(body, "UniversalUser");
            ParseUtil.batchSaveInParseTable(body, "University");

        } catch (Exception e) {

        }

        return null;
    }

}
