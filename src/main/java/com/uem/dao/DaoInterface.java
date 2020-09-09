package com.uem.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.uem.model.*;
import org.json.JSONObject;

import java.util.List;

public interface DaoInterface {

    public String test();
    public CustomResponse signUp(String email, String type);
    public CustomResponse signIn(String email, String password, String loginType);
    public Boolean updateUserInfo(JSONObject body);
    public CustomResponse getUserInfo(String UserID);
    public CustomResponse geAdminInfo(String AdminID);
    public CustomResponse geCourseAdminInfo(String AdminID);
    public CustomResponse geStudentInfo(String AdminID);
    public CustomResponse geTeacherInfo(String AdminID);

    public CustomResponse getAllBatches();
    public CustomResponse getAllBatches(JSONObject body);
    public CustomResponse getAllCourses();
    public CustomResponse getAllCourses(JSONObject body);
    public CustomResponse getAllPosts(JSONObject body);

    public CustomResponse createCourse(JSONObject body);
    public CustomResponse getUniversity(String UnivID);
    public CustomResponse createUniversity(JSONObject body);
    public CustomResponse updateUniversity(JSONObject body, Boolean append);
    public CustomResponse updateAdmin(JSONObject body, Boolean append);
    public CustomResponse updateStudent(JSONObject body, Boolean append);
    public CustomResponse updateTeacher(JSONObject body, Boolean append);

    public CustomResponse createPost(JSONObject body);
    public CustomResponse updatePost(JSONObject body, Boolean append);

    public CustomResponse createMessage(JSONObject body);
    public CustomResponse getAllMessages(JSONObject body);

    public CustomResponse createNotification(JSONObject body);
    public CustomResponse getAllNotifications(JSONObject body);
    public CustomResponse updateNotification(JSONObject body, Boolean append);

    public CustomResponse createBatch(JSONObject body);
    public CustomResponse updateBatch(JSONObject body, Boolean append);
    public CustomResponse deleteFromBatch(JSONObject body);

}
