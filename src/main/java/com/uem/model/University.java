package com.uem.model;

public class University {

    private String UnivID;
    private String Name;
    private Object Photo;
    private String Started;
    private String UnivAdmins;
    private String Students;
    private String Teachers;
    private String Courses;
    private String Website;
    private String MoreInfo;
    private String ActionLogs;

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

    public Object getPhoto() {
        return Photo;
    }

    public void setPhoto(Object photo) {
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
