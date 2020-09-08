package com.uem.model;

public class Connections {

    private String UserID;
    private String ConnectionUserID;
    private String accepted;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getConnectionUserID() {
        return ConnectionUserID;
    }

    public void setConnectionUserID(String connectionUserID) {
        ConnectionUserID = connectionUserID;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }
}
