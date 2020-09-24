package com.uem.model;

import org.bson.Document;
import org.json.JSONArray;

import java.util.Date;
import java.util.List;

public class UnivAdmin {

    private String UEM_ID;
    private String UserID;
    private String UnivID;
    private String info;
    private List<Document> Documents;
    private Document Photo;

    private String password;
    private String aboutMe;
    private String institution;
    private String profession;
    private String lastLogin;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Document getPhoto() {
        return Photo;
    }

    public void setPhoto(Document photo) {
        Photo = photo;
    }

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

    public List<Document> getDocuments() {
        return Documents;
    }

    public void setDocuments(List<Document> documents) {
        Documents = documents;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

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
        return "UnivAdmin{" +
                "UEM_ID='" + UEM_ID + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UnivID='" + UnivID + '\'' +
                '}';
    }
}
