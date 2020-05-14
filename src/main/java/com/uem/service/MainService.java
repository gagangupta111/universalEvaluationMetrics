package com.uem.service;

import com.uem.dao.DaoInterface;
import com.uem.model.CustomResponse;
import com.uem.model.User;
import com.uem.util.LogUtil;
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

    public CustomResponse signUp(String email) {
        return dao.signUp(email);
    }

    public CustomResponse signIN(String email, String password, String loginType) {
        return dao.signIn(email, password, loginType);
    }

    public CustomResponse createUniversity(JSONObject body) {
        return dao.createUniversity(body);
    }

    public CustomResponse updateUniversity(JSONObject body) {
        return dao.updateUniversity(body);
    }

    public Boolean updateUserInfo(JSONObject body) {
        return dao.updateUserInfo(body);
    }

    public List<User> getUserInfo(String UserID) {
        return dao.getUserInfo(UserID);
    }
}
