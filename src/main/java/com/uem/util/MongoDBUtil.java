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

public class MongoDBUtil {

    static Logger logger = LogUtil.getInstance();

    public static MongoClient mongo_client = null;
    public static MongoDatabase mongo_database = null;

    public static MongoCollection<Document> TEST = null;
    public static MongoCollection<Document> Batch = null;
    public static MongoCollection<Document> Course = null;
    public static MongoCollection<Document> CourseAdmin = null;
    public static MongoCollection<Document> Permissions = null;
    public static MongoCollection<Document> Student = null;
    public static MongoCollection<Document> Teacher = null;
    public static MongoCollection<Document> UnivAdmin = null;
    public static MongoCollection<Document> UniversalUser = null;
    public static MongoCollection<Document> University = null;
    public static MongoCollection<Document> Messages = null;
    public static MongoCollection<Document> Posts = null;
    public static MongoCollection<Document> notifications = null;
    public static MongoCollection<Document> connections = null;
    public static MongoCollection<Document> events = null;
    public static MongoCollection<Document> logs = null;
    public static MongoCollection<Document> Module = null;
    public static MongoCollection<Document> Level = null;
    public static MongoCollection<Document> Questions = null;

    public static MongoDatabase getDataBase() {

        if (null == mongo_client) {
            mongo_client = new MongoClient(new MongoClientURI("mongodb://admin:GuBlzZ98mdqugwpR5MPH6Eir@mongodb.back4app.com:27017/395cf4d3118743a4aa44c598d899689d?ssl=true"));
        }
        if (null == mongo_database) {
            mongo_database = mongo_client.getDatabase("395cf4d3118743a4aa44c598d899689d");
        }
        return mongo_database;
    }

    public static MongoCollection<Document> getConnections() {
        if (null == connections) {
            connections = getDataBase().getCollection("Connections");
            return connections;
        } else {
            return connections;
        }
    }

    public static MongoCollection<Document> getLogs() {
        if (null == logs) {
            logs = getDataBase().getCollection("Logs");
            return logs;
        } else {
            return logs;
        }
    }

    public static MongoCollection<Document> getNotifications() {
        if (null == notifications) {
            notifications = getDataBase().getCollection("Notifications");
            return notifications;
        } else {
            return notifications;
        }
    }

    public static MongoCollection<Document> getTest() {
        if (null == TEST) {
            TEST = getDataBase().getCollection("TEST");
            return TEST;
        } else {
            return TEST;
        }
    }

    public static MongoCollection<Document> getEvents() {
        if (null == events) {
            events = getDataBase().getCollection("Events");
            return events;
        } else {
            return events;
        }
    }

    public static MongoCollection<Document> getMessages() {
        if (null == Messages) {
            Messages = getDataBase().getCollection("Messages");
            return Messages;
        } else {
            return Messages;
        }
    }

    public static MongoCollection<Document> getBatch() {
        if (null == Batch) {
            Batch = getDataBase().getCollection("Batch");
            return Batch;
        } else {
            return Batch;
        }
    }

    public static MongoCollection<Document> getPosts() {
        if (null == Course) {
            Course = getDataBase().getCollection("Posts");
            return Course;
        } else {
            return Course;
        }
    }

    public static MongoCollection<Document> getCourse() {
        if (null == Course) {
            Course = getDataBase().getCollection("Course");
            return Course;
        } else {
            return Course;
        }
    }

    public static MongoCollection<Document> getCourseAdmin() {
        if (null == CourseAdmin) {
            CourseAdmin = getDataBase().getCollection("CourseAdmin");
            return CourseAdmin;
        } else {
            return CourseAdmin;
        }
    }

    public static MongoCollection<Document> getPermissions() {
        if (null == Permissions) {
            Permissions = getDataBase().getCollection("Permissions");
            return Permissions;
        } else {
            return Permissions;
        }
    }

    public static MongoCollection<Document> getStudent() {
        if (null == Student) {
            Student = getDataBase().getCollection("Student");
            return Student;
        } else {
            return Student;
        }
    }

    public static MongoCollection<Document> getTeacher() {
        if (null == Teacher) {
            Teacher = getDataBase().getCollection("Teacher");
            return Teacher;
        } else {
            return Teacher;
        }
    }

    public static MongoCollection<Document> getUnivAdmin() {
        if (null == UnivAdmin) {
            UnivAdmin = getDataBase().getCollection("UnivAdmin");
            return UnivAdmin;
        } else {
            return UnivAdmin;
        }
    }

    public static MongoCollection<Document> getUniversalUser() {
        if (null == UniversalUser) {
            UniversalUser = getDataBase().getCollection("UniversalUser");
            return UniversalUser;
        } else {
            return UniversalUser;
        }
    }

    public static MongoCollection<Document> getQuestions() {
        if (null == Questions) {
            Questions = getDataBase().getCollection("Questions");
            return Questions;
        } else {
            return Questions;
        }
    }

    public static MongoCollection<Document> getLevels() {
        if (null == Level) {
            Level = getDataBase().getCollection("Level");
            return Level;
        } else {
            return Level;
        }
    }

    public static MongoCollection<Document> getModules() {
        if (null == Module) {
            Module = getDataBase().getCollection("Module");
            return Module;
        } else {
            return Module;
        }
    }

    public static MongoCollection<Document> getUniversity() {
        if (null == University) {
            University = getDataBase().getCollection("University");
            return University;
        } else {
            return University;
        }
    }

    public static List<Document> getAllTestObjects(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getTest();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllTestObjects \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllTestObjects", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllConnections(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getConnections();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllConnections \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllConnections", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllNotifications(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getNotifications();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllNotifications \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllNotifications", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllEvents(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getEvents();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllEvents \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllEvents", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllMessages(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getMessages();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllMessages \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllMessages", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }


    public static List<Document> getAllBatch(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getBatch();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllBatch \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllBatch", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllPosts(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getPosts();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getPosts \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllCourse", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllCourse(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getCourse();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllCourse \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllCourse", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllCourseAdmin(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getCourseAdmin();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllCourseAdmin \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllCourseAdmin", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllPermissions(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getPermissions();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllPermissions \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllPermissions", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllStudents(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getStudent();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllStudents \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllStudents", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllCourseAdmins(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getCourseAdmin();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllCourseAdmins \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllCourseAdmins", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllTeachers(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getTeacher();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllTeachers \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllTeachers", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllQuestions(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getQuestions();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllQuestions \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllQuestions", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllLevels(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getLevels();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllLevels \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllLevels", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllModules(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getModules();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllUniversity \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllUniversity", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllUniversity(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getUniversity();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllUniversity \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllUniversity", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllUniversityAdmin(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getUnivAdmin();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllUniversityAdmin \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllUniversityAdmin", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> getAllUniversalUsers(BsonDocument filter ) {

        try {
            MongoCollection<Document> collection = MongoDBUtil.getUniversalUser();
            List<Document> allDocuments = collection.find(filter).into(new ArrayList<Document>());
            return allDocuments;
        } catch (Exception e) {
            logger.info("EXCEPTION : CLASS - MONGOOP | METHOD - getAllUniversalUsers \n" + UtilsManager.exceptionAsString(e));
            RollbarManager.sendExceptionOnRollBar("getAllUniversalUsers", UtilsManager.exceptionAsString(e));
            return new ArrayList<>();
        }
    }

    public static List<Document> DUMMY(String insightLevelsComma, String statusCompleted, int days, String accountIds) {

        try {

            int hours = days * 24;

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -hours);
            Date oneHourBack = cal.getTime();

            DateTime dateStr = new org.joda.time.DateTime(oneHourBack.getTime(), org.joda.time.DateTimeZone.UTC);

            MongoCollection<Document> collection = getTest();
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