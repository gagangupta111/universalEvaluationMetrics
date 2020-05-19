package com.uem.dao;

import com.uem.model.*;
import org.json.JSONObject;

import java.util.List;

public interface DaoInterface {

    public String test();
    public CustomResponse signUp(String email, String type);
    public CustomResponse signIn(String email, String password, String loginType);
    public Boolean updateUserInfo(JSONObject body);
    public List<User> getUserInfo(String UserID);
    public List<UnivAdmin> geAdminInfo(String AdminID);
    public List<Student> geStudentInfo(String AdminID);
    public List<Teacher> geTeacherInfo(String AdminID);
    // geTeacherInfo

    public CustomResponse createUniversity(JSONObject body);
    public CustomResponse updateUniversity(JSONObject body, Boolean append);
    public CustomResponse updateAdmin(JSONObject body, Boolean append);
    public CustomResponse updateStudent(JSONObject body, Boolean append);
    public CustomResponse updateTeacher(JSONObject body, Boolean append);
    //updateTeacher
}
