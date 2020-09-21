package com.uem.model;

import org.bson.Document;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public class User {

    private String UserID;
    private String Email;
    private String Password;
    private String Name;
    private String Mobile;
    private Document Photo;
    private String Address;
    private String DOB;
    private String info;

    private String aboutMe;
    private String profession;
    private String institution;
    private String lastLogin;

    String objectID;
    private Date _created_at;
    private Date _updated_at;

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(Email, user.Email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(Email);
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public Document getPhoto() {
        return Photo;
    }

    public void setPhoto(Document photo) {
        Photo = photo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID='" + UserID + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Name='" + Name + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Photo='" + Photo + '\'' +
                ", Address='" + Address + '\'' +
                ", DOB='" + DOB + '\'' +
                '}';
    }
}
