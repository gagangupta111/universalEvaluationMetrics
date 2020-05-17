package com.uem.dao;

import com.uem.model.CustomResponse;
import com.uem.model.University;
import com.uem.model.User;
import org.json.JSONObject;

import java.util.List;

public interface DaoInterface {

    public String test();
    public CustomResponse signUp(String email);
    public CustomResponse signIn(String email, String password, String loginType);
    public Boolean updateUserInfo(JSONObject body);
    public List<User> getUserInfo(String UserID);
    public List<User> geAdminInfo(String AdminID);
    // geAdminInfo

    public CustomResponse createUniversity(JSONObject body);
    public CustomResponse updateUniversity(JSONObject body, Boolean append);
    //updateUserInfo
}
