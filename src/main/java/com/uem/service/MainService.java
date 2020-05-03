package com.uem.service;

import com.uem.dao.DaoInterface;
import com.uem.model.CustomResponse;
import com.uem.util.LogUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private static Logger logger = LogUtil.getInstance();

    @Autowired
    @Qualifier("DaoBigQuery")
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

}
