package com.uem.dao;

import com.uem.util.AllDBOperations;
import com.uem.model.*;
import com.uem.util.Constants;
import com.uem.util.LogUtil;
import com.uem.util.UtilsManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Qualifier("DaoParse")
public class DaoParse implements DaoInterface {

    private static Logger logger = LogUtil.getInstance();

    public String test() {
        logger.debug("REQUEST_RECIEVED-DaoParse");
        return "TEST-DaoParse";
    }

    @Override
    public CustomResponse signUp(String email, String type){

        try {
            List<User> users = AllDBOperations.getAllUsers_Email(email);
            if (users == null || users.size() == 0) {

                Map<String, Object> data = AllDBOperations.createUser(email);

                if (Boolean.valueOf(String.valueOf(data.get("success")))){

                    JSONObject body = new JSONObject( String.valueOf(data.get("body")));
                    Map<String, Object> adminResponse = new HashMap<>();

                    if (Constants.ADMIN.equals(type.toUpperCase())){
                        adminResponse = AllDBOperations.createAdmin(body.getString("UserID"));
                    }else if (Constants.TEACHER.equals(type.toUpperCase())){
                        adminResponse = AllDBOperations.createTeacher(body.getString("UserID"));
                    }else if (Constants.STUDENT.equals(type.toUpperCase())){
                        adminResponse = AllDBOperations.createStudent(body.getString("UserID"));
                    }else if (Constants.COURSE_ADMIN.equals(type.toUpperCase())){
                        adminResponse = AllDBOperations.createCourseAdmin(body.getString("UserID"));
                    }

                    if (Boolean.valueOf(String.valueOf(adminResponse.get("success")))){
                        JSONObject adminBody = new JSONObject( String.valueOf(adminResponse.get("body")));
                        body.put("UEM_ID", adminBody.getString("UEM_ID"));
                        data.put("body", body);

                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(true);
                        customResponse.setMessage(Constants.SUCCESS);
                        customResponse.setInfo(data);
                        return customResponse;
                    }else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.SIGN_UP_FAILURE);
                        return customResponse;
                    }
                }else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.SIGN_UP_FAILURE);
                    return customResponse;
                }
            } else {

                Map<String, Object> data = new HashMap<>();

                Map<String, Object> adminResponse = new HashMap<>();
                if (Constants.ADMIN.equals(type.toUpperCase())){
                    List<UnivAdmin> univAdmins = AllDBOperations.getAllAdmin_UserID(users.get(0).getUserID());
                    if (univAdmins == null || univAdmins.size() == 0){
                        adminResponse = AllDBOperations.createAdmin(users.get(0).getUserID());
                    }else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.ALREADY_EXIST);
                        return customResponse;
                    }
                }else if (Constants.TEACHER.equals(type.toUpperCase())){
                    List<Teacher> univAdmins = AllDBOperations.getAllTeachers_UserID(users.get(0).getUserID());
                    if (univAdmins == null || univAdmins.size() == 0){
                        adminResponse = AllDBOperations.createTeacher(users.get(0).getUserID());
                    }else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.ALREADY_EXIST);
                        return customResponse;
                    }
                }else if (Constants.STUDENT.equals(type.toUpperCase())){
                    List<Student> univAdmins = AllDBOperations.getAllStudents_UserID(users.get(0).getUserID());
                    if (univAdmins == null || univAdmins.size() == 0){
                        adminResponse = AllDBOperations.createStudent(users.get(0).getUserID());
                    }else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.ALREADY_EXIST);
                        return customResponse;
                    }
                }else if (Constants.COURSE_ADMIN.equals(type.toUpperCase())){
                    List<CourseAdmin> univAdmins = AllDBOperations.getAllCourseAdmins_UserID(users.get(0).getUserID());
                    if (univAdmins == null || univAdmins.size() == 0){
                        adminResponse = AllDBOperations.createCourseAdmin(users.get(0).getUserID());
                    }else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.ALREADY_EXIST);
                        return customResponse;
                    }
                }

                if (Boolean.valueOf(String.valueOf(adminResponse.get("success")))){
                    data.put("success", true);
                    JSONObject body = new JSONObject();

                    JSONObject adminBody = new JSONObject( String.valueOf(adminResponse.get("body")));
                    body.put("UEM_ID", adminBody.getString("UEM_ID"));
                    body.put("UserID", users.get(0).getUserID());
                    data.put("body", body);

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(data);
                    return customResponse;
                }else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.SIGN_UP_FAILURE);
                    return customResponse;
                }
            }
        }catch (Exception e){
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.SIGN_UP_FAILURE);
            Map<String, Object> info = new HashMap<>();
            info.put("exception", UtilsManager.exceptionAsString(e));
            customResponse.setInfo(info);
            return customResponse;
        }
    }

    @Override
    public CustomResponse signIn(String email, String password, String loginType) {

        List<User> users = AllDBOperations.getAllUsers_Email(email);

        if (users == null || users.size() == 0 || !password.equals(users.get(0).getPassword())) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.LOGIN_FAILURE);
            return customResponse;
        }

        switch (loginType) {
            case "ADMIN":
                List<UnivAdmin> univAdmins = AllDBOperations.getAllAdmin_UserID(users.get(0).getUserID());
                if (univAdmins == null || univAdmins.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.ADMIN_LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            case "STUDENT":
                List<Student> students = AllDBOperations.getAllStudents_UserID(users.get(0).getUserID());
                if (students == null || students.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.STUDENT_LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            case "TEACHER":
                List<Teacher> teachers = AllDBOperations.getAllTeachers_UserID(users.get(0).getUserID());
                if (teachers == null || teachers.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.TEACHER_LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            default:
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.INTERNAL_ERROR);
                return customResponse;
        }

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        return customResponse;
    }

    @Override
    public Boolean updateUserInfo(JSONObject body) {

        try {

            Map<String, Object> map = AllDBOperations.updateUser(body);
            if (Boolean.valueOf(String.valueOf(map.get("success")))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return false;
        }
    }

    @Override
    public List<User> getUserInfo(String UserID) {
        List<User> users = new ArrayList<>();
        users = AllDBOperations.getAllUsers_UserID(UserID);
        return users;
    }

    @Override
    public List<UnivAdmin> geAdminInfo(String AdminID) {
        List<UnivAdmin> users = new ArrayList<>();
        users = AllDBOperations.getAllAdmin_UemID(AdminID);
        return users;
    }

    @Override
    public List<CourseAdmin> geCourseAdminInfo(String AdminID) {
        List<CourseAdmin> users = new ArrayList<>();
        users = AllDBOperations.getAllCourseAdmins_UemID(AdminID);
        return users;
    }

    @Override
    public List<Student> geStudentInfo(String StudentID) {
        List<Student> users = new ArrayList<>();
        users = AllDBOperations.getAllStudents_UemID(StudentID);
        return users;
    }

    @Override
    public List<Teacher> geTeacherInfo(String StudentID) {
        List<Teacher> users = new ArrayList<>();
        users = AllDBOperations.getAllTeachers_UemID(StudentID);
        return users;
    }

    @Override
    public List<University> getUniversity(String UnivID){
        List<University> users = new ArrayList<>();
        users = AllDBOperations.getAllUniversities_UnivID(UnivID);
        return users;

    }

    @Override
    public CustomResponse createUniversity(JSONObject body) {

        try {
            if (body.has("Name") && body.has("Website") && body.has("AdminID")) {

                Map<String, Object> map = AllDBOperations.createUniversity(body);
                if (map == null || Boolean.valueOf(String.valueOf(map.get("success"))) == false) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            } else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.UNIVERSITY_CREATION_FAILURE);
                return customResponse;
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.INTERNAL_ERROR);

            Map<String, Object> map = new HashMap<>();
            map.put("exception", UtilsManager.exceptionAsString(e));
            customResponse.setInfo(map);
            return customResponse;
        }
    }

    @Override
    public CustomResponse updateUniversity(JSONObject body, Boolean append) {

        try {

            List<University> universities = AllDBOperations.getAllUniversities_UnivID(body.getString("UnivID"));
            if (universities == null || universities.size() == 0){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.UNIVERSITY_DOES_NOT_EXIST);
                return customResponse;
            }else {
                Map<String, Object> map = AllDBOperations.updateUniversity(universities.get(0), body, append);
                if (map == null || Boolean.valueOf(String.valueOf(map.get("success"))) == false) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            }
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.INTERNAL_ERROR);

            Map<String, Object> map = new HashMap<>();
            map.put("exception", UtilsManager.exceptionAsString(e));
            customResponse.setInfo(map);
            return customResponse;
        }
    }

    @Override
    public CustomResponse updateAdmin(JSONObject body, Boolean append) {

        try {

            List<UnivAdmin> univAdmins = AllDBOperations.getAllAdmin_UemID(body.getString("adminID"));
            if (univAdmins == null || univAdmins.size() == 0){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.ADMIN_DOES_NOT_EXIST);
                return customResponse;
            }else {
                Map<String, Object> map = AllDBOperations.updateAdmin(univAdmins.get(0), body, append);
                if (map == null || Boolean.valueOf(String.valueOf(map.get("success"))) == false) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            }
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.INTERNAL_ERROR);

            Map<String, Object> map = new HashMap<>();
            map.put("exception", UtilsManager.exceptionAsString(e));
            customResponse.setInfo(map);
            return customResponse;
        }
    }

    @Override
    public CustomResponse updateStudent(JSONObject body, Boolean append) {

        try {

            List<Student> students = AllDBOperations.getAllStudents_UemID(body.getString("studentID"));
            if (students == null || students.size() == 0){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.STUDENT_DOES_NOT_EXIST);
                return customResponse;
            }else {
                Map<String, Object> map = AllDBOperations.updateStudent(students.get(0), body, append);
                if (map == null || Boolean.valueOf(String.valueOf(map.get("success"))) == false) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            }
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.INTERNAL_ERROR);

            Map<String, Object> map = new HashMap<>();
            map.put("exception", UtilsManager.exceptionAsString(e));
            customResponse.setInfo(map);
            return customResponse;
        }
    }

    @Override
    public CustomResponse updateTeacher(JSONObject body, Boolean append) {

        try {

            List<Teacher> teachers = AllDBOperations.getAllTeachers_UemID(body.getString("teacherID"));
            if (teachers == null || teachers.size() == 0){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.TEACHER_DOES_NOT_EXIST);
                return customResponse;
            }else {
                Map<String, Object> map = AllDBOperations.updateTeacher(teachers.get(0), body, append);
                if (map == null || Boolean.valueOf(String.valueOf(map.get("success"))) == false) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            }
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.INTERNAL_ERROR);

            Map<String, Object> map = new HashMap<>();
            map.put("exception", UtilsManager.exceptionAsString(e));
            customResponse.setInfo(map);
            return customResponse;
        }
    }

    // updateStudent
}
