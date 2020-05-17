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
    public CustomResponse signUp(String email) {
        List<User> users = AllDBOperations.getAllUsers_Email(email);
        if (users == null || users.size() == 0) {

            Map<String, Object> data = AllDBOperations.createUser(email);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setInfo(data);
            customResponse.setMessage(Constants.SUCCESS);
            return customResponse;
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.SIGN_UP_FAILURE);
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
}
