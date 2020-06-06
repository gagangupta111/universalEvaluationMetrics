package com.uem.model;

import org.bson.Document;
import java.util.Date;
import java.util.List;

public class Batch {

    private String objectID;
    private Date _created_at;
    private Date _updated_at;

    private String batchID;
    private String courseID;
    private Document billing;
    private Document Photo;
    // student, lead teacher, fellow teacher
    private String duration;
    private String spanOver;
    private String starting;
    private String completion;
    private String AdminID;
    private Document Calendar;
    private Document status;
    private Document info;

    private List<Document> leadTutors;
    private List<Document> fellowTutors;
    private List<Document> students;
    private List<Document> actionLogs;

    public Document getPhoto() {
        return Photo;
    }

    public void setPhoto(Document photo) {
        Photo = photo;
    }

    public String getAdminID() {
        return AdminID;
    }

    public void setAdminID(String adminID) {
        AdminID = adminID;
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

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Document getBilling() {
        return billing;
    }

    public void setBilling(Document billing) {
        this.billing = billing;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSpanOver() {
        return spanOver;
    }

    public void setSpanOver(String spanOver) {
        this.spanOver = spanOver;
    }

    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public Document getCalendar() {
        return Calendar;
    }

    public void setCalendar(Document calendar) {
        Calendar = calendar;
    }

    public Document getStatus() {
        return status;
    }

    public void setStatus(Document status) {
        this.status = status;
    }

    public Document getInfo() {
        return info;
    }

    public void setInfo(Document info) {
        this.info = info;
    }

    public List<Document> getLeadTutors() {
        return leadTutors;
    }

    public void setLeadTutors(List<Document> leadTutors) {
        this.leadTutors = leadTutors;
    }

    public List<Document> getFellowTutors() {
        return fellowTutors;
    }

    public void setFellowTutors(List<Document> fellowTutors) {
        this.fellowTutors = fellowTutors;
    }

    public List<Document> getStudents() {
        return students;
    }

    public void setStudents(List<Document> students) {
        this.students = students;
    }

    public List<Document> getActionLogs() {
        return actionLogs;
    }

    public void setActionLogs(List<Document> actionLogs) {
        this.actionLogs = actionLogs;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                ", batchID='" + batchID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", billing=" + billing +
                ", Photo=" + Photo +
                ", duration='" + duration + '\'' +
                ", spanOver='" + spanOver + '\'' +
                ", starting='" + starting + '\'' +
                ", completion='" + completion + '\'' +
                ", AdminID='" + AdminID + '\'' +
                ", Calendar=" + Calendar +
                ", status=" + status +
                ", info=" + info +
                ", leadTutors=" + leadTutors +
                ", fellowTutors=" + fellowTutors +
                ", students=" + students +
                ", actionLogs=" + actionLogs +
                '}';
    }
}
