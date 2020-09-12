package com.uem.model;

import java.util.Date;

public class Event {

    private String text;
    private String EventID;
    private String time;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" +
                "text='" + text + '\'' +
                ", EventID='" + EventID + '\'' +
                ", time='" + time + '\'' +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }
}
