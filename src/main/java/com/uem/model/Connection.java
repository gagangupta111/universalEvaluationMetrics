package com.uem.model;

import java.util.Date;

public class Connection {

    private String From;
    private String To;
    private String status;
    private String ConnectionID;

    private String objectID;
    private Date _created_at;
    private Date _updated_at;

    @Override
    public String toString() {
        return "Connection{" +
                "From='" + From + '\'' +
                ", To='" + To + '\'' +
                ", status='" + status + '\'' +
                ", ConnectionID='" + ConnectionID + '\'' +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }

    public String getConnectionID() {
        return ConnectionID;
    }

    public void setConnectionID(String connectionID) {
        ConnectionID = connectionID;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
