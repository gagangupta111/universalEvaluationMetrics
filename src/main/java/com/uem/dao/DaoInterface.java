package com.uem.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.uem.model.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface DaoInterface {

    public String test();
    public CustomResponse signUp(String email, String password, String type);
    public CustomResponse signUp_2(String email, String type);
    public CustomResponse signIn(String email, String password, String loginType);
    public CustomResponse signIn2(String email, String password);

    public Boolean updateUserInfo(JSONObject body);
    public Boolean updateUserInfo_Email(JSONObject body);
    public Boolean updateUserInfo_Email_Type(JSONObject body);
    public Boolean updateUser_Only_Photo(JSONObject body);
    public Boolean updateUser_Only_Photo_Type(JSONObject body);

    public CustomResponse getUserInfo(String UserID, String type);
    public CustomResponse getAllUserInfo();
    public CustomResponse getUserInfo_Email(String email);
    public CustomResponse getAllUsers_By_Key(String key);

    public CustomResponse geAdminInfo(String AdminID);
    public CustomResponse geCourseAdminInfo(String AdminID);
    public CustomResponse geStudentInfo(String AdminID);
    public CustomResponse geTeacherInfo(String AdminID);

    public CustomResponse getAllBatches();
    public CustomResponse getAllBatches(JSONObject body);
    public CustomResponse getAllCourses();
    public CustomResponse getAllCourses(JSONObject body);
    public CustomResponse getAllPosts(JSONObject body);
    public CustomResponse isPostToggledByUser(JSONObject body);

    public CustomResponse createCourse(JSONObject body);
    public CustomResponse getUniversity(String UnivID);

    public CustomResponse getUniversity(JSONObject body);
    public CustomResponse getModules(JSONObject body);
    public CustomResponse getLevels_Filter(JSONObject body);

    public CustomResponse getModules(String UnivID);
    public CustomResponse getModules_By_ModuleID(String UnivID);
    public CustomResponse createUniversity(JSONObject body);
    public CustomResponse updateUniversity(JSONObject body, Boolean append);
    public CustomResponse updateUniversity_New(JSONObject body);
    public CustomResponse updateAdmin(JSONObject body, Boolean append);
    public CustomResponse updateStudent(JSONObject body, Boolean append);
    public CustomResponse updateTeacher(JSONObject body, Boolean append);

    public CustomResponse createModules(JSONObject body);
    public CustomResponse updateModule(JSONObject body);

    public CustomResponse getLevels_By_ModuleID(String UnivID);
    public CustomResponse updateLevel(JSONObject body);
    public CustomResponse createLevels(JSONObject body);
    public CustomResponse getLevels_By_LevelID(String UnivID);

    public CustomResponse createQuestions(JSONObject body);
    public CustomResponse get_Questions_By_QuestionID(String UnivID);
    public CustomResponse get_Questions_By_LevelID(String UnivID);
    public CustomResponse getQuestions_Filter(JSONObject body);
    public CustomResponse updateQuestion(JSONObject body);

    public CustomResponse get_All_Modules_Student(JSONObject body);
    public CustomResponse get_All_Levels_Student(JSONObject body);
    public CustomResponse get_Question_Answer_Student(JSONObject body);

    // get_All_Answers_Teacher
    public CustomResponse get_All_Questions_Teacher(JSONObject body);
    public CustomResponse get_All_Answers_Teacher(JSONObject body);
    public CustomResponse get_All_Modules_Teacher(JSONObject body);
    public CustomResponse get_All_Levels_Teacher(JSONObject body);

    public CustomResponse get_all_teachers(JSONObject body);
    public CustomResponse get_all_students(JSONObject body);

    public CustomResponse teacher_reports(JSONObject body);
    public CustomResponse student_reports(JSONObject body);

    public CustomResponse createAnswers(JSONObject body);
    public CustomResponse getAnswers_Filter(JSONObject body);
    public CustomResponse updateAnswer(JSONObject body);

    public CustomResponse createPost(JSONObject body);
    public CustomResponse updatePost(JSONObject body, Boolean append);

    public CustomResponse createMessage(JSONObject body);
    public CustomResponse updateMessage(JSONObject body, Boolean append);
    public CustomResponse getAllMessages(JSONObject body);
    public CustomResponse getAllMessages_ReadByMe(JSONObject body);

    public CustomResponse getAllMessengers(JSONObject body);

    public CustomResponse createEvent(JSONObject body);
    public CustomResponse getAllEvents();

    public CustomResponse createNotification(JSONObject body);
    public CustomResponse getAllNotifications(JSONObject body);
    public CustomResponse updateNotification(JSONObject body, Boolean append);
    public CustomResponse updateNotifications_read_all(JSONObject body, Boolean append);

    public CustomResponse createConnection(JSONObject body);
    public CustomResponse getAllConnection(JSONObject body);
    public CustomResponse updateConnection(JSONObject body, Boolean append);

    public CustomResponse searchAllConnection(JSONObject body);

    public CustomResponse createBatch(JSONObject body);
    public CustomResponse updateBatch(JSONObject body, Boolean append);
    public CustomResponse deleteFromBatch(JSONObject body);

    public CustomResponse createVisitor(JSONObject body);

}
