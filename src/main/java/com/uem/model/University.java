package com.uem.model;

import org.bson.Document;

import java.util.Date;
import java.util.List;

public class University {

    private String UnivID;
    private String Name;
    private Document Photo;
    private String Started;
    private String UnivAdmins;
    private List<Document> Students;
    private List<Document> Teachers;
    private List<Document> Courses;
    private String Website;
    private Document MoreInfo;
    private Document LegalInfo;
    private List<Document> ActionLogs;
    private String info;

    private String objectID;
    private Date _created_at;
    private Date _updated_at;

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

    public Document getPhoto() {
        return Photo;
    }

    public void setPhoto(Document photo) {
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

    public List<Document> getStudents() {
        return Students;
    }

    public void setStudents(List<Document> students) {
        Students = students;
    }

    public List<Document> getTeachers() {
        return Teachers;
    }

    public void setTeachers(List<Document> teachers) {
        Teachers = teachers;
    }

    public List<Document> getCourses() {
        return Courses;
    }

    public void setCourses(List<Document> courses) {
        Courses = courses;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public Document getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(Document moreInfo) {
        MoreInfo = moreInfo;
    }

    public Document getLegalInfo() {
        return LegalInfo;
    }

    public void setLegalInfo(Document legalInfo) {
        LegalInfo = legalInfo;
    }

    public List<Document> getActionLogs() {
        return ActionLogs;
    }

    public void setActionLogs(List<Document> actionLogs) {
        ActionLogs = actionLogs;
    }

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

    @Override
    public String toString() {
        return "University{" +
                "UnivID='" + UnivID + '\'' +
                ", Name='" + Name + '\'' +
                ", Photo=" + Photo +
                ", Started='" + Started + '\'' +
                ", UnivAdmins='" + UnivAdmins + '\'' +
                ", Students=" + Students +
                ", Teachers=" + Teachers +
                ", Courses=" + Courses +
                ", Website='" + Website + '\'' +
                ", MoreInfo='" + MoreInfo + '\'' +
                ", LegalInfo='" + LegalInfo + '\'' +
                ", ActionLogs=" + ActionLogs +
                ", info='" + info + '\'' +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }
}
