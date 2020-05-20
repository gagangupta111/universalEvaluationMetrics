package com.uem.model;

import org.bson.Document;
import java.util.Date;
import java.util.List;

public class Course {

    private String courseID;
    private String name;
    private String level;
    private String levelInt;
    private Document billing;
    private Document status;
    private List<Document> batches;
    private List<Document> universities;
    private List<Document>  actionLogs;
    private String objectID;
    private Date _created_at;
    private Date _updated_at;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelInt() {
        return levelInt;
    }

    public void setLevelInt(String levelInt) {
        this.levelInt = levelInt;
    }

    public Document getBilling() {
        return billing;
    }

    public void setBilling(Document billing) {
        this.billing = billing;
    }

    public Document getStatus() {
        return status;
    }

    public void setStatus(Document status) {
        this.status = status;
    }

    public List<Document> getBatches() {
        return batches;
    }

    public void setBatches(List<Document> batches) {
        this.batches = batches;
    }

    public List<Document> getUniversities() {
        return universities;
    }

    public void setUniversities(List<Document> universities) {
        this.universities = universities;
    }

    public List<Document> getActionLogs() {
        return actionLogs;
    }

    public void setActionLogs(List<Document> actionLogs) {
        this.actionLogs = actionLogs;
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


}
