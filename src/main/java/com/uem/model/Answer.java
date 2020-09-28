package com.uem.model;

import org.bson.Document;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Answer {

    private String StudentID;
    private String TeacherID;
    private String TeacherComments;
    private String TeacherRatings;

    private String ModuleID;
    private String UnivID;

    private String QuestionID;
    private String AnswerID;
    private String LevelID;
    private String info;

    private List<Document> Images;
    private List<Document> Videos;

    String objectID;
    private Date _created_at;
    private Date _updated_at;

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getAnswerID() {
        return AnswerID;
    }

    public void setAnswerID(String answerID) {
        AnswerID = answerID;
    }

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }

    public String getTeacherComments() {
        return TeacherComments;
    }

    public void setTeacherComments(String teacherComments) {
        TeacherComments = teacherComments;
    }

    public String getTeacherRatings() {
        return TeacherRatings;
    }

    public void setTeacherRatings(String teacherRatings) {
        TeacherRatings = teacherRatings;
    }

    public String getModuleID() {
        return ModuleID;
    }

    public void setModuleID(String moduleID) {
        ModuleID = moduleID;
    }

    public String getUnivID() {
        return UnivID;
    }

    public void setUnivID(String univID) {
        UnivID = univID;
    }

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
        Answer answer = (Answer) o;
        return Objects.equals(AnswerID, answer.AnswerID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(AnswerID);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "ModuleID='" + ModuleID + '\'' +
                ", UnivID='" + UnivID + '\'' +
                ", QuestionID='" + QuestionID + '\'' +
                ", AnswerID='" + AnswerID + '\'' +
                ", LevelID='" + LevelID + '\'' +
                ", info='" + info + '\'' +
                ", Images=" + Images +
                ", Videos=" + Videos +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }
}