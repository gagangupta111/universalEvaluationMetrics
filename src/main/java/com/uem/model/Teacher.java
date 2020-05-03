package com.uem.model;

public class Teacher {

    private String UEM_ID;
    private String UserID;
    private String UnivID;

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
        return "Teacher{" +
                "UEM_ID='" + UEM_ID + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UnivID='" + UnivID + '\'' +
                '}';
    }
}
