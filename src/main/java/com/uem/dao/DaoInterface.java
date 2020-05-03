package com.uem.dao;

import com.uem.model.CustomResponse;
import org.json.JSONObject;

public interface DaoInterface {

    public String test();
    public CustomResponse signUp(String email);

}
