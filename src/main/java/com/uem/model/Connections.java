package com.uem.model;

import java.util.Date;

public class Connections {

    private String UserID;
    private String ConnectionUserID;
    private String accepted;

    private String objectID;
    private Date _created_at;
    private Date _updated_at;

    @Override
    public String toString() {
        return "Connections{" +
                "UserID='" + UserID + '\'' +
                ", ConnectionUserID='" + ConnectionUserID + '\'' +
                ", accepted='" + accepted + '\'' +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
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
