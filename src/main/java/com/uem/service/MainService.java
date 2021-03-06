package com.uem.service;

import com.uem.dao.DaoInterface;
import com.uem.model.*;
import com.uem.util.LogUtil;
import com.uem.util.ParseUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    private static Logger logger = LogUtil.getInstance();

    @Autowired
    @Qualifier("DaoParse")
    private DaoInterface dao;

    public String test() {
        logger.debug("REQUEST_RECIEVED-MainService");
        return dao.test();
    }

    public String deleteAll() {
        ParseUtil.deleteAllObjectsAllTables();
        return "Ok";
    }

    public String createDummy() {
        ParseUtil.createDummyObjectsAllTables();
        return "Ok";
    }

    public CustomResponse signUp_2(String email, String password) {
        return dao.signUp_2(email, password);
    }

    public CustomResponse signUp(String email, String password,  String type) {
        return dao.signUp(email, password, type);
    }

    public CustomResponse signIN2(String email, String password) {
        return dao.signIn2(email, password);
    }

    public CustomResponse signIN(String email, String password, String loginType) {
        return dao.signIn(email, password, loginType);
    }

    public CustomResponse get_Questions_By_LevelID(String ModuleID) {
        return dao.get_Questions_By_LevelID(ModuleID);
    }

    public CustomResponse get_Levels_By_LevelID(String ModuleID) {
        return dao.getLevels_By_LevelID(ModuleID);
    }

    public CustomResponse getModules_By_ModuleID(String ModuleID) {
        return dao.getModules_By_ModuleID(ModuleID);
    }

    public CustomResponse get_Questions_QuestionID(String univID) {
        return dao.get_Questions_By_QuestionID(univID);
    }

    public CustomResponse getlevels_By_ModuleID(String univID) {
        return dao.getLevels_By_ModuleID(univID);
    }

    public CustomResponse getModules(String univID) {
        return dao.getModules(univID);
    }

    public CustomResponse getUniversity(String univID) {
        return dao.getUniversity(univID);
    }

    public CustomResponse getUniversity(JSONObject body) {
        return dao.getUniversity(body);
    }

    public CustomResponse get_Answers_Filter(JSONObject body) {
        return dao.getAnswers_Filter(body);
    }

    // get_Question_Answer_Student

    public CustomResponse get_Question_Answer_Student(JSONObject body) {
        return dao.get_Question_Answer_Student(body);
    }

    public CustomResponse student_reports(JSONObject body) {
        return dao.student_reports(body);
    }

    public CustomResponse get_all_teachers(JSONObject body) {
        return dao.get_all_teachers(body);
    }

    public CustomResponse get_all_students(JSONObject body) {
        return dao.get_all_students(body);
    }

    public CustomResponse teacher_reports(JSONObject body) {
        return dao.teacher_reports(body);
    }

    public CustomResponse get_All_Answers_Teacher(JSONObject body) {
        return dao.get_All_Answers_Teacher(body);
    }

    public CustomResponse get_All_Questions_Teacher(JSONObject body) {
        return dao.get_All_Questions_Teacher(body);
    }

    public CustomResponse get_All_Levels_Teacher(JSONObject body) {
        return dao.get_All_Levels_Teacher(body);
    }

    public CustomResponse get_All_Levels_Student(JSONObject body) {
        return dao.get_All_Levels_Student(body);
    }

    public CustomResponse get_All_Modules_Teacher(JSONObject body) {
        return dao.get_All_Modules_Teacher(body);
    }

    public CustomResponse get_All_Modules_Student(JSONObject body) {
        return dao.get_All_Modules_Student(body);
    }

    public CustomResponse get_Questions_Filter(JSONObject body) {
        return dao.getQuestions_Filter(body);
    }

    public CustomResponse getLevels_Filter(JSONObject body) {
        return dao.getLevels_Filter(body);
    }

    public CustomResponse getModules(JSONObject body) {
        return dao.getModules(body);
    }

    public CustomResponse createModules(JSONObject body) {
        return dao.createModules(body);
    }

    public CustomResponse createAnswers(JSONObject body) {
        return dao.createAnswers(body);
    }

    public CustomResponse createQuestions(JSONObject body) {
        return dao.createQuestions(body);
    }

    public CustomResponse createLevels(JSONObject body) {
        return dao.createLevels(body);
    }

    public CustomResponse createUniversity(JSONObject body) {
        return dao.createUniversity(body);
    }

    public CustomResponse createPost(JSONObject body) {
        return dao.createPost(body);
    }

    public CustomResponse createEvent(JSONObject body) {
        return dao.createEvent(body);
    }

    public CustomResponse createMessage(JSONObject body) {
        return dao.createMessage(body);
    }

    public CustomResponse createNotification(JSONObject body) {
        return dao.createNotification(body);
    }

    public CustomResponse createConnection(JSONObject body) {
        return dao.createConnection(body);
    }

    public CustomResponse updateNotification(JSONObject body) {
        return dao.updateNotification(body, true);
    }

    public CustomResponse updateNotifications_read_all(JSONObject body) {
        return dao.updateNotifications_read_all(body, true);
    }

    public CustomResponse updateConnection(JSONObject body) {
        return dao.updateConnection(body, true);
    }

    public CustomResponse updatePost(JSONObject body) {
        return dao.updatePost(body, true);
    }

    public CustomResponse updateMessage(JSONObject body) {
        return dao.updateMessage(body, true);
    }

    public CustomResponse createBatch(JSONObject body) {
        return dao.createBatch(body);
    }

    public CustomResponse updateUniversity(JSONObject body, Boolean append) {
        return dao.updateUniversity(body, append);
    }

    public CustomResponse update_level_Images(JSONObject body) {
        return dao.updateLevel(body);
    }

    public CustomResponse update_Answer(JSONObject body) {
        return dao.updateAnswer(body);
    }

    public CustomResponse update_Question(JSONObject body) {
        return dao.updateQuestion(body);
    }

    public CustomResponse update_level(JSONObject body) {
        return dao.updateLevel(body);
    }

    public CustomResponse update_module(JSONObject body) {
        return dao.updateModule(body);
    }

    public CustomResponse updateUniversity_New(JSONObject body) {
        return dao.updateUniversity_New(body);
    }

    public CustomResponse updateBatch(JSONObject body, Boolean append) {
        return dao.updateBatch(body, append);
    }

    public CustomResponse deleteFromBatch(JSONObject body) {
        return dao.deleteFromBatch(body);
    }

    public CustomResponse updateAdmin(JSONObject body, Boolean append) {
        return dao.updateAdmin(body, append);
    }

    public CustomResponse updateStudent(JSONObject body, Boolean append) {
        return dao.updateStudent(body, append);
    }

    public CustomResponse updateTeacher(JSONObject body, Boolean append) {
        return dao.updateTeacher(body, append);
    }

    public Boolean updateUserInfo(JSONObject body) {
        return dao.updateUserInfo(body);
    }

    public Boolean updateUser_Only_Photo(JSONObject body) {
        return dao.updateUser_Only_Photo(body);
    }

    public Boolean updateUser_Only_Photo_Type(JSONObject body) {
        return dao.updateUser_Only_Photo_Type(body);
    }

    public Boolean updateUserInfo_Email(JSONObject body) {
        return dao.updateUserInfo_Email(body);
    }

    public Boolean updateUserInfo_Email_type(JSONObject body) {
        return dao.updateUserInfo_Email_Type(body);
    }

    public CustomResponse getAllUserInfo() {
        return dao.getAllUserInfo();
    }

    public CustomResponse getUserInfo(String UserID, String type) {
        return dao.getUserInfo(UserID, type);
    }

    public CustomResponse getUserInfo_Email(String UserID) {
        return dao.getUserInfo_Email(UserID);
    }

    public CustomResponse getAllUsers_By_Key(String key) {
        return dao.getAllUsers_By_Key(key);
    }

    public CustomResponse createCourse(JSONObject body) {
        return dao.createCourse(body);
    }

    public CustomResponse getAllEvents() {
        return dao.getAllEvents();
    }

    public CustomResponse getAllPosts(JSONObject body) {
        return dao.getAllPosts(body);
    }

    public CustomResponse isPostToggledByUser(JSONObject body) {
        return dao.isPostToggledByUser(body);
    }

    public CustomResponse getAllMessengers(JSONObject body) {
        return dao.getAllMessengers(body);
    }

    public CustomResponse getAllMessages(JSONObject body) {
        return dao.getAllMessages(body);
    }

    public CustomResponse getAllMessages_ReadByMe(JSONObject body) {
        return dao.getAllMessages_ReadByMe(body);
    }

    public CustomResponse getAllNotifications(JSONObject body) {
        return dao.getAllNotifications(body);
    }

    public CustomResponse getAllConnections(JSONObject body) {
        return dao.getAllConnection(body);
    }

    public CustomResponse searchAllConnections(JSONObject body) {
        return dao.searchAllConnection(body);
    }

    public CustomResponse getAllCourses() {
        return dao.getAllCourses();
    }

    public CustomResponse getAllBatches() {
        return dao.getAllBatches();
    }

    public CustomResponse getAllBatches(JSONObject body) {
        return dao.getAllBatches(body);
    }

    public CustomResponse getAllCourses(JSONObject body) {
        return dao.getAllCourses(body);
    }

    public CustomResponse geAdminInfo(String AdminID) {
        return dao.geAdminInfo(AdminID);
    }

    public CustomResponse geCourseAdminInfo(String AdminID) {
        return dao.geCourseAdminInfo(AdminID);
    }

    public CustomResponse geStudentInfo(String studentID) {
        return dao.geStudentInfo(studentID);
    }

    public CustomResponse geTeacherInfo(String teacherID) {
        return dao.geTeacherInfo(teacherID);
    }

    public CustomResponse createVisitor(JSONObject body) {
        return dao.createVisitor(body);
    }

}
