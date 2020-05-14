package com.uem.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestClass {

    private String objectID;
    private String col1;
    private JSONArray col2;
    private JSONObject col3;
    private String createdAt;
    private String updatedAt;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public JSONArray getCol2() {
        return col2;
    }

    public void setCol2(JSONArray col2) {
        this.col2 = col2;
    }

    public JSONObject getCol3() {
        return col3;
    }

    public void setCol3(JSONObject col3) {
        this.col3 = col3;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "objectID='" + objectID + '\'' +
                ", col1='" + col1 + '\'' +
                ", col2=" + col2 +
                ", col3=" + col3 +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
