package com.uem.model;

import java.util.Date;
import java.util.List;

public class Message {

    private String From;
    private String To;
    private String text;
    private String read;
    private String MessageID;
    private List<String> readBy;

    private String objectID;
    private Date _created_at;
    private Date _updated_at;

    public List<String> getReadBy() {
        return readBy;
    }

    public void setReadBy(List<String> readBy) {
        this.readBy = readBy;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
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

    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "From='" + From + '\'' +
                ", To='" + To + '\'' +
                ", text='" + text + '\'' +
                ", read='" + read + '\'' +
                ", MessageID='" + MessageID + '\'' +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }
}
