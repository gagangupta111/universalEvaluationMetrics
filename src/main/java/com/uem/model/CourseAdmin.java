package com.uem.model;

import org.bson.Document;
import java.util.Date;
import java.util.List;

public class CourseAdmin {

    private String objectID;
    private Date _created_at;
    private Date _updated_at;

    private String UEM_ID;
    private String UserID;
    private String info;
    private List<Document> Documents;
    private List<Document> Courses;
    private Document Photo;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Document> getDocuments() {
        return Documents;
    }

    public void setDocuments(List<Document> documents) {
        Documents = documents;
    }

    public List<Document> getCourses() {
        return Courses;
    }

    public void setCourses(List<Document> courses) {
        this.Courses = courses;
    }

    public Document getPhoto() {
        return Photo;
    }

    public void setPhoto(Document photo) {
        Photo = photo;
    }
}
