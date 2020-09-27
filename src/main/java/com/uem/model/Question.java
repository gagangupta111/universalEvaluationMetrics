package com.uem.model;

import org.bson.Document;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Question {

    private String QuestionID;
    private String LevelID;
    private String Name;
    private Document Photo;
    private String info;

    private List<Document> Images;
    private List<Document> Videos;

    String objectID;
    private Date _created_at;
    private Date _updated_at;

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public List<Document> getImages() {
        return Images;
    }

    public void setImages(List<Document> images) {
        Images = images;
    }

    public List<Document> getVideos() {
        return Videos;
    }

    public void setVideos(List<Document> videos) {
        Videos = videos;
    }

    public String getLevelID() {
        return LevelID;
    }

    public void setLevelID(String levelID) {
        LevelID = levelID;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question level = (Question) o;
        return Objects.equals(LevelID, level.LevelID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(LevelID);
    }

    @Override
    public String toString() {
        return "Question{" +
                ", LevelID='" + LevelID + '\'' +
                ", Name='" + Name + '\'' +
                ", Photo=" + Photo +
                ", info='" + info + '\'' +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }
}