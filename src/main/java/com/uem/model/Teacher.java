package com.uem.model;

import org.bson.Document;
import org.json.JSONArray;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Teacher {

    private String UEM_ID;
    private String UserID;
    private String UnivID;
    private String info;
    private List<Document> Documents;
    private Document Photo;

    public Document getPhoto() {
        return Photo;
    }

    public void setPhoto(Document photo) {
        Photo = photo;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getUEM_ID(), teacher.getUEM_ID()) &&
                Objects.equals(getUserID(), teacher.getUserID()) &&
                Objects.equals(getUnivID(), teacher.getUnivID()) &&
                Objects.equals(getObjectID(), teacher.getObjectID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUEM_ID(), getUserID(), getUnivID(), getObjectID());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "UEM_ID='" + UEM_ID + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UnivID='" + UnivID + '\'' +
                ", info='" + info + '\'' +
                ", Documents=" + Documents +
                ", Photo=" + Photo +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }
}
