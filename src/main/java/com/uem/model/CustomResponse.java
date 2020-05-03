package com.uem.model;

import org.json.JSONObject;

import java.util.Map;

public class CustomResponse {

    private Boolean success;
    private String message;
    private Map<String, Object> info;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public JSONObject getInfoAsJson() {
        JSONObject jsonObject = new JSONObject();
        for (String key : info.keySet()){
            try {
                jsonObject.put(key, info.get(key));
            }catch (Exception e){}
        }
        return jsonObject;
    }

}
