package com.uem.model;

import org.json.JSONObject;

import java.util.Date;

public class University {

    private String UnivID;
    private String Name;
    private JSONObject Photo;
    private String Started;
    private String UnivAdmins;
    private String Students;
    private String Teachers;
    private String Courses;
    private String Website;
    private String MoreInfo;
    private String ActionLogs;
    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getUnivID() {
        return UnivID;
    }

    public void setUnivID(String univID) {
        UnivID = univID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public JSONObject getPhoto() {
        return Photo;
    }

    public void setPhoto(JSONObject photo) {
        Photo = photo;
    }

    public String getStarted() {
        return Started;
    }

    public void setStarted(String started) {
        Started = started;
    }

    public String getUnivAdmins() {
        return UnivAdmins;
    }

    public void setUnivAdmins(String univAdmins) {
        UnivAdmins = univAdmins;
    }

    public String getStudents() {
        return Students;
    }

    public void setStudents(String students) {
        Students = students;
    }

    public String getTeachers() {
        return Teachers;
    }

    public void setTeachers(String teachers) {
        Teachers = teachers;
    }

    public String getCourses() {
        return Courses;
    }

    public void setCourses(String courses) {
        Courses = courses;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        MoreInfo = moreInfo;
    }

    public String getActionLogs() {
        return ActionLogs;
    }

    public void setActionLogs(String actionLogs) {
        ActionLogs = actionLogs;
    }

    @Override
    public String toString() {
        return "University{" +
                "UnivID='" + UnivID + '\'' +
                ", Name='" + Name + '\'' +
                ", Photo=" + Photo +
                ", Started='" + Started + '\'' +
                ", UnivAdmins='" + UnivAdmins + '\'' +
                ", Students='" + Students + '\'' +
                ", Teachers='" + Teachers + '\'' +
                ", Courses='" + Courses + '\'' +
                ", Website='" + Website + '\'' +
                ", MoreInfo='" + MoreInfo + '\'' +
                ", ActionLogs='" + ActionLogs + '\'' +
                '}';
    }
}
