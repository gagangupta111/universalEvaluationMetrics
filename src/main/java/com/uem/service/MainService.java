package com.uem.service;

import com.uem.dao.DaoInterface;
import com.uem.model.*;
import com.uem.util.LogUtil;
import com.uem.util.ParseUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    private static Logger logger = LogUtil.getInstance();

    @Autowired
    @Qualifier("DaoParse")
    private DaoInterface dao;

    public String test() {
        logger.debug("REQUEST_RECIEVED-MainService");
        return dao.test();
    }

    public String deleteAll() {
        ParseUtil.deleteAllObjectsAllTables();
        return "Ok";
    }

    public String createDummy() {
        ParseUtil.createDummyObjectsAllTables();
        return "Ok";
    }

    public CustomResponse signUp(String email, String type) {
        return dao.signUp(email, type);
    }

    public CustomResponse signIN(String email, String password, String loginType) {
        return dao.signIn(email, password, loginType);
    }

    public CustomResponse getUniversity(String univID) {
        return dao.getUniversity(univID);
    }

    public CustomResponse createUniversity(JSONObject body) {
        return dao.createUniversity(body);
    }

    public CustomResponse createBatch(JSONObject body) {
        return dao.createBatch(body);
    }

    public CustomResponse updateUniversity(JSONObject body, Boolean append) {
        return dao.updateUniversity(body, append);
    }

    public CustomResponse updateBatch(JSONObject body, Boolean append) {
        return dao.updateBatch(body, append);
    }

    public CustomResponse deleteFromBatch(JSONObject body) {
        return dao.deleteFromBatch(body);
    }

    public CustomResponse updateAdmin(JSONObject body, Boolean append) {
        return dao.updateAdmin(body, append);
    }

    public CustomResponse updateStudent(JSONObject body, Boolean append) {
        return dao.updateStudent(body, append);
    }

    public CustomResponse updateTeacher(JSONObject body, Boolean append) {
        return dao.updateTeacher(body, append);
    }

    public Boolean updateUserInfo(JSONObject body) {
        return dao.updateUserInfo(body);
    }

    public CustomResponse getUserInfo(String UserID) {
        return dao.getUserInfo(UserID);
    }

    public CustomResponse createCourse(JSONObject body) {
        return dao.createCourse(body);
    }

    public CustomResponse getAllCourses() {
        return dao.getAllCourses();
    }

    public CustomResponse getAllBatches() {
        return dao.getAllBatches();
    }

    public CustomResponse getAllBatches(JSONObject body) {
        return dao.getAllBatches(body);
    }

    public CustomResponse getAllCourses(JSONObject body) {
        return dao.getAllCourses(body);
    }

    public CustomResponse geAdminInfo(String AdminID) {
        return dao.geAdminInfo(AdminID);
    }

    public CustomResponse geCourseAdminInfo(String AdminID) {
        return dao.geCourseAdminInfo(AdminID);
    }

    public CustomResponse geStudentInfo(String studentID) {
        return dao.geStudentInfo(studentID);
    }

    public CustomResponse geTeacherInfo(String teacherID) {
        return dao.geTeacherInfo(teacherID);
    }


}
