package com.uem.model;

import org.bson.Document;

import java.util.Date;
import java.util.Objects;

public class Module {

    private String UnivID;
    private String ModuleID;
    private String Name;
    private Document Photo;
    private String info;

    String objectID;
    private Date _created_at;
    private Date _updated_at;

    public String getUnivID() {
        return UnivID;
    }

    public void setUnivID(String univID) {
        UnivID = univID;
    }

    public String getModuleID() {
        return ModuleID;
    }

    public void setModuleID(String moduleID) {
        ModuleID = moduleID;
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
        Module module = (Module) o;
        return Objects.equals(ModuleID, module.ModuleID) &&
                Objects.equals(objectID, module.objectID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ModuleID, objectID);
    }

    @Override
    public String toString() {
        return "Module{" +
                "UnivID='" + UnivID + '\'' +
                ", ModuleID='" + ModuleID + '\'' +
                ", Name='" + Name + '\'' +
                ", Photo=" + Photo +
                ", info='" + info + '\'' +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }
}