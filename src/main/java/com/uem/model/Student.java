package com.uem.model;

import org.bson.Document;
import org.json.JSONArray;

import java.util.Date;
import java.util.List;

public class Student {

    private String UEM_ID;
    private String UserID;
    private String UnivID;
    private String info;
    private List<Document> Documents;


    private String objectID;
    private Date _created_at;
    private Date _updated_at;
    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public Date get_created_at() {
        return _created_at;
    }

    public void set_created_at(Date _created_at) {
        this._created_at = _created_at;
    }

    public Date get_updated_at() {
        return _updated_at;
    }

    public void set_updated_at(Date _updated_at) {
        this._updated_at = _updated_at;
    }


    public List<Document> getDocuments() {
        return Documents;
    }

    public void setDocuments(List<Document> documents) {
        Documents = documents;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getUEM_ID() {
        return UEM_ID;
    }

    public void setUEM_ID(String UEM_ID) {
        this.UEM_ID = UEM_ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUnivID() {
        return UnivID;
    }

    public void setUnivID(String univID) {
        UnivID = univID;
    }

    @Override
    public String toString() {
        return "Student{" +
                "UEM_ID='" + UEM_ID + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UnivID='" + UnivID + '\'' +
                '}';
    }
}
