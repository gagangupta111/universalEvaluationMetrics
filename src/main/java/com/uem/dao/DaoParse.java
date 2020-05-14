package com.uem.dao;

import com.uem.util.AllDBOperations;
import com.uem.model.*;
import com.uem.util.Constants;
import com.uem.util.LogUtil;
import com.uem.util.UtilsManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
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

        List<User> users = new ArrayList<>();
        users = AllDBOperations.getAllUsers_Email(email);

        if (users == null || users.size() == 0 || !password.equals(users.get(0).getPassword())) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.LOGIN_FAILURE);
            return customResponse;
        }

        switch (loginType) {
            case "ADMIN":
                List<UnivAdmin> univAdmins = AllDBOperations.getAllAdmin(users.get(0).getUserID());
                if (univAdmins == null || univAdmins.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.ADMIN_LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            case "STUDENT":
                List<Student> students = AllDBOperations.getAllStudents(users.get(0).getUserID());
                if (students == null || students.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.STUDENT_LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            case "TEACHER":
                List<Teacher> teachers = AllDBOperations.getAllTeachers(users.get(0).getUserID());
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
            User user = new User();
            user.setUserID(body.getString("UserID"));
            Iterator<String> keys = body.keys();
            while (keys.hasNext()) {

                String key = keys.next();
                switch (key) {
                    case "Name":
                        user.setName(body.getString(key));
                        break;
                    case "Mobile":
                        user.setMobile(body.getString(key));
                        break;
                    case "Photo":
                        user.setPhoto(body.getString(key));
                        break;
                    case "Address":
                        user.setAddress(body.getString(key));
                        break;
                    case "DOB":
                        user.setDOB(body.getString(key));
                        break;
                    default:
                        break;
                }
            }

            return AllDBOperations.updateUser(user);

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
    public CustomResponse createUniversity(JSONObject body) {

        try {
            if (body.has("Name") && body.has("Website") && body.has("AdminID")) {

                Map<String, Object> map = AllDBOperations.createUniversity(body);
                if (map == null) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
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
            return customResponse;
        }
    }

    @Override
    public CustomResponse updateUniversity(JSONObject body) {

        try {
            Boolean replace = body.has("Replace") ? body.getBoolean("Replace") : false;
            List<University> universities = AllDBOperations.getAllUniversities_UnivID(body.getString("UnivID"));
            University existingUniversity = null;

            if (universities == null || universities.size() == 0){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.UNIVERSITY_DOES_NOT_EXIST);
                return customResponse;
            }else {
                existingUniversity = universities.get(0);
                University university = new University();
                university.setUnivID(body.getString("UnivID"));

                if (body.has("Name")){
                    university.setName(body.getString("Name"));
                }else if (body.has("Photo")){
                    university.setPhoto(body.getString("Photo"));
                }else if (body.has("UnivAdmins")){
                    if (replace){
                        university.setUnivAdmins(body.getString("UnivAdmins"));
                    }else {
                        university.setUnivAdmins(existingUniversity.getUnivAdmins() + "," + body.getString("UnivAdmins"));
                    }
                }else if (body.has("Students")){
                    String students = existingUniversity.getStudents();
                    if (students == null || students.equals("")){
                        students = body.getString("Students");
                    }else {
                        students = students + "," + body.getString("Students");
                    }
                    university.setStudents(students);
                    if (replace){
                        university.setStudents(body.getString("Students"));
                    }
                }else if (body.has("Teachers")){
                    String teachers = existingUniversity.getTeachers();
                    if (teachers == null || teachers.equals("")){
                        teachers = body.getString("Teachers");
                    }else {
                        teachers = teachers + "," + body.getString("Teachers");
                    }
                    university.setTeachers(teachers);
                    if (replace){
                        university.setTeachers(body.getString("Teachers"));
                    }
                }else if (body.has("Courses")){
                    String courses = existingUniversity.getCourses();
                    if (courses == null || courses.equals("")){
                        courses = body.getString("Courses");
                    }else {
                        courses = courses + "," + body.getString("Courses");
                    }
                    university.setCourses(courses);
                    if (replace){
                        university.setCourses(body.getString("Courses"));
                    }
                }else if (body.has("Website")){
                    university.setWebsite(body.getString("Website"));
                }else if (body.has("MoreInfo")){
                    String moreInfo = existingUniversity.getMoreInfo();
                    if (moreInfo == null || moreInfo.equals("")){
                        moreInfo = body.getString("MoreInfo");
                    }else {
                        moreInfo = moreInfo + "," + body.getString("MoreInfo");
                    }
                    university.setMoreInfo(moreInfo);
                    if (replace){
                        university.setMoreInfo(body.getString("MoreInfo"));
                    }
                }

                // Add "ActionLogs" as this Update.
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Action", "University_Updated");
                jsonObject.put("Time", UtilsManager.getUTCStandardDateFormat());
                jsonObject.put("Values", body.toString());

                String actionLogs = existingUniversity.getActionLogs();
                JSONArray array = new JSONArray(actionLogs);
                array.put(jsonObject);
                university.setActionLogs(array.toString());

                if (AllDBOperations.updateUniversity(university)){
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    return customResponse;
                }else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.FAILURE);
                    return customResponse;
                }
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.INTERNAL_ERROR);
            Map<String, Object> map = new HashMap<>();
            map.put("details", UtilsManager.exceptionAsString(e));
            customResponse.setInfo(map);
            return customResponse;
        }
    }

}
