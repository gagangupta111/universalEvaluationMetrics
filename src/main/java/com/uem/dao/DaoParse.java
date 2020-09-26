package com.uem.dao;

import com.google.api.services.bigquery.model.JsonObject;
import com.uem.util.AllDBOperations;
import com.uem.model.*;
import com.uem.util.Constants;
import com.uem.util.LogUtil;
import com.uem.util.UtilsManager;
import javafx.geometry.Pos;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
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
    public CustomResponse signUp_2(String email, String password) {

        try {
            List<User> users = AllDBOperations.getAllUsers_Email(email);
            if (users != null && users.size() > 0) {

                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.ALREADY_EXIST);
                return customResponse;

            } else {
                Map<String, Object> data = AllDBOperations.createUser_2(email, password);

                if (Boolean.valueOf(String.valueOf(data.get("success")))) {

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(data);
                    return customResponse;

                } else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.SIGN_UP_FAILURE);
                    return customResponse;
                }
            }
        } catch (Exception e) {
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
    public CustomResponse signUp(String email, String password, String type) {

        try {
            List<User> users = AllDBOperations.getAllUsers_Email(email);
            if (users == null || users.size() == 0) {

                Map<String, Object> data = AllDBOperations.createUser(email, password);

                if (Boolean.valueOf(String.valueOf(data.get("success")))) {

                    JSONObject body = new JSONObject(String.valueOf(data.get("body")));
                    Map<String, Object> adminResponse = new HashMap<>();

                    if (Constants.ADMIN.equals(type.toUpperCase())) {
                        adminResponse = AllDBOperations.createAdmin(email, password);
                    } else if (Constants.TEACHER.equals(type.toUpperCase())) {
                        adminResponse = AllDBOperations.createTeacher(email, password);
                    } else if (Constants.STUDENT.equals(type.toUpperCase())) {
                        adminResponse = AllDBOperations.createStudent(email, password);
                    } else if (Constants.COURSE_ADMIN.equals(type.toUpperCase())) {
                        adminResponse = AllDBOperations.createCourseAdmin(email, password);
                    }

                    if (Boolean.valueOf(String.valueOf(adminResponse.get("success")))) {
                        JSONObject adminBody = new JSONObject(String.valueOf(adminResponse.get("body")));
                        body.put("UEM_ID", adminBody.getString("UEM_ID"));
                        data.put("body", body);

                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(true);
                        customResponse.setMessage(Constants.SUCCESS);
                        customResponse.setInfo(data);
                        return customResponse;
                    } else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.SIGN_UP_FAILURE);
                        return customResponse;
                    }
                } else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.SIGN_UP_FAILURE);
                    return customResponse;
                }
            } else {

                Map<String, Object> data = new HashMap<>();

                Map<String, Object> adminResponse = new HashMap<>();
                if (Constants.ADMIN.equals(type.toUpperCase())) {
                    List<UnivAdmin> univAdmins = AllDBOperations.getAllAdmin_UserID(email);
                    if (univAdmins == null || univAdmins.size() == 0) {
                        adminResponse = AllDBOperations.createAdmin(email, password);
                    } else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.ALREADY_EXIST);
                        return customResponse;
                    }
                } else if (Constants.TEACHER.equals(type.toUpperCase())) {
                    List<Teacher> univAdmins = AllDBOperations.getAllTeachers_UserID(email);
                    if (univAdmins == null || univAdmins.size() == 0) {
                        adminResponse = AllDBOperations.createTeacher(email, password);
                    } else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.ALREADY_EXIST);
                        return customResponse;
                    }
                } else if (Constants.STUDENT.equals(type.toUpperCase())) {
                    List<Student> univAdmins = AllDBOperations.getAllStudents_UserID(email);
                    if (univAdmins == null || univAdmins.size() == 0) {
                        adminResponse = AllDBOperations.createStudent(email, password);
                    } else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.ALREADY_EXIST);
                        return customResponse;
                    }
                } else if (Constants.COURSE_ADMIN.equals(type.toUpperCase())) {
                    List<CourseAdmin> univAdmins = AllDBOperations.getAllCourseAdmins_UserID(email);
                    if (univAdmins == null || univAdmins.size() == 0) {
                        adminResponse = AllDBOperations.createCourseAdmin(email, password);
                    } else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.ALREADY_EXIST);
                        return customResponse;
                    }
                }

                if (Boolean.valueOf(String.valueOf(adminResponse.get("success")))) {
                    data.put("success", true);
                    JSONObject body = new JSONObject();

                    JSONObject adminBody = new JSONObject(String.valueOf(adminResponse.get("body")));
                    body.put("UEM_ID", adminBody.getString("UEM_ID"));
                    body.put("UserID", email);
                    data.put("body", body);

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(data);
                    return customResponse;
                } else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.SIGN_UP_FAILURE);
                    return customResponse;
                }
            }
        } catch (Exception e) {
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
    public CustomResponse signIn2(String email, String password) {

        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        List<User> users = AllDBOperations.getAllUsers_Email(email);

        if (users == null || users.size() == 0 || !password.equals(users.get(0).getPassword())) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.LOGIN_FAILURE);
            return customResponse;
        }

        if (password.equals(users.get(0).getPassword())){

            try {
                JSONObject updateUser = new JSONObject();
                updateUser.put("Email", email);
                updateUser.put("lastLogin", UtilsManager.getUTCStandardDateFormat());
                updateUserInfo_Email(updateUser);
            }catch (Exception e){}

            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setInfo(data);
            customResponse.setMessage(Constants.SUCCESS);
            return customResponse;
        }else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.LOGIN_FAILURE);
            return customResponse;
        }
    }

    @Override
    public CustomResponse signIn(String email, String password, String loginType) {

        List<User> users = AllDBOperations.getAllUsers_Email(email);

        if (users == null || users.size() == 0) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.LOGIN_FAILURE);
            return customResponse;
        }

        switch (loginType) {
            case "ADMIN":
                List<UnivAdmin> univAdmins = AllDBOperations.getAllAdmin_UserID(email);
                if (univAdmins == null || univAdmins.size() == 0 || !password.equalsIgnoreCase(univAdmins.get(0).getPassword())) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            case "STUDENT":
                List<Student> students = AllDBOperations.getAllStudents_UserID(email);
                if (students == null || students.size() == 0 || !password.equalsIgnoreCase(students.get(0).getPassword())) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            case "TEACHER":
                List<Teacher> teachers = AllDBOperations.getAllTeachers_UserID(email);
                if (teachers == null || teachers.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.LOGIN_FAILURE);
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
        Map<String, Object> info = new HashMap<>();
        customResponse.setInfo(info);
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

    // updateUser_Only_Photo_Type
    @Override
    public Boolean updateUser_Only_Photo_Type(JSONObject body) {

        try {

            Map<String, Object> map = AllDBOperations.updateUser_By_Email_Only_Photo_Type(body);
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
    public Boolean updateUser_Only_Photo(JSONObject body) {

        try {

            Map<String, Object> map = AllDBOperations.updateUser_By_Email_Only_Photo(body);
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

    // updateUserInfo_Email_Type
    @Override
    public Boolean updateUserInfo_Email_Type(JSONObject body) {

        try {

            Map<String, Object> map = AllDBOperations.updateUser_Email_Type(body);
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
    public Boolean updateUserInfo_Email(JSONObject body) {

        try {

            Map<String, Object> map = AllDBOperations.updateUser_Email(body);
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
    public CustomResponse getAllUsers_By_Key(String key) {
        List<User> users = new ArrayList<>();
        users = AllDBOperations.getAllUsers_By_Key(key);

        JSONArray array = new JSONArray();
        for (User univAdmin : users){
            array.put(UtilsManager.userToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Users",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    @Override
    public CustomResponse getUserInfo_Email(String email) {
        List<User> users = new ArrayList<>();
        users = AllDBOperations.getAllUsers_Email(email);

        JSONArray array = new JSONArray();
        for (User univAdmin : users){
            array.put(UtilsManager.userToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Users",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    @Override
    public CustomResponse getAllUserInfo() {
        List<User> users = new ArrayList<>();
        users = AllDBOperations.getAllUsers();

        JSONArray array = new JSONArray();
        for (User univAdmin : users){
            array.put(UtilsManager.userToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Users",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    @Override
    public CustomResponse getUserInfo(String email, String type) {

        try {
            JSONObject user = new JSONObject();
            switch (type.toUpperCase()) {
                case "ADMIN":
                    List<UnivAdmin> univAdmins = AllDBOperations.getAllAdmin_UserID(email);
                    if (univAdmins == null || univAdmins.size() == 0) {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.LOGIN_FAILURE);
                        return customResponse;
                    }else {
                        user = UtilsManager.adminToJson(univAdmins.get(0));
                    }
                    break;
                case "STUDENT":
                    List<Student> students = AllDBOperations.getAllStudents_UserID(email);
                    if (students == null || students.size() == 0) {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.LOGIN_FAILURE);
                        return customResponse;
                    }else {
                        user = UtilsManager.studentToJson(students.get(0));
                    }
                    break;
                case "TEACHER":
                    List<Teacher> teachers = AllDBOperations.getAllTeachers_UserID(email);
                    if (teachers == null || teachers.size() == 0) {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.LOGIN_FAILURE);
                        return customResponse;
                    }else {
                        user = UtilsManager.teacherToJson(teachers.get(0));
                    }
                    break;
                default:
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    return customResponse;
            }

            List<User> userList = AllDBOperations.getAllUsers_Email(email);
            user.put("Name", userList.get(0).getName());
            user.put("Mobile", userList.get(0).getMobile());
            user.put("DOB", userList.get(0).getDOB());
            user.put("Address", userList.get(0).getAddress());
            Map<String, Object> map = new HashMap<>();
            map.put("User",  user);

            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;
        }catch (Exception e){
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.INTERNAL_ERROR);
            return customResponse;
        }
    }

    @Override
    public CustomResponse geAdminInfo(String AdminID) {

        List<UnivAdmin> users = new ArrayList<>();
        users = AllDBOperations.getAllAdmin_UemID(AdminID);

        JSONArray array = new JSONArray();
        for (UnivAdmin univAdmin : users){
            array.put(UtilsManager.adminToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Admins",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    @Override
    public CustomResponse geCourseAdminInfo(String AdminID) {
        List<CourseAdmin> users = new ArrayList<>();
        users = AllDBOperations.getAllCourseAdmins_UemID(AdminID);

        JSONArray array = new JSONArray();
        for (CourseAdmin univAdmin : users){
            array.put(UtilsManager.courseAdminToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("CourseAdmins",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    @Override
    public CustomResponse geStudentInfo(String StudentID) {
        List<Student> users = new ArrayList<>();
        users = AllDBOperations.getAllStudents_UemID(StudentID);

        JSONArray array = new JSONArray();
        for (Student univAdmin : users){
            array.put(UtilsManager.studentToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Students",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;

    }

    @Override
    public CustomResponse geTeacherInfo(String StudentID) {
        List<Teacher> users = new ArrayList<>();
        users = AllDBOperations.getAllTeachers_UemID(StudentID);

        JSONArray array = new JSONArray();
        for (Teacher univAdmin : users){
            array.put(UtilsManager.teacherToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Students",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    // isPostToggledByUser
    @Override
    public CustomResponse isPostToggledByUser(JSONObject body) {

        try {
            JSONObject search = new JSONObject();
            search.put("PostID", body.getString("PostID"));
            List<Post> posts = AllDBOperations.getAllPostsInUEM(search);

            if (body.getString("action").equalsIgnoreCase("likesBy")){

                if (posts.get(0).getLikesBy() != null && posts.get(0).getLikesBy().toString().contains(body.getString("UserID"))){
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    Map<String, Object> json = new HashMap<>();
                    json.put("toggled", "true");
                    customResponse.setInfo(json);
                    return customResponse;
                }else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    Map<String, Object> json = new HashMap<>();
                    json.put("toggled", "false");
                    customResponse.setInfo(json);
                    return customResponse;
                }

            }else if (body.getString("action").equalsIgnoreCase("sharesBy")){

                if (posts.get(0).getSharesBy() != null && posts.get(0).getSharesBy().toString().contains(body.getString("UserID"))){
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    Map<String, Object> json = new HashMap<>();
                    json.put("toggled", "true");
                    customResponse.setInfo(json);
                    return customResponse;
                }else {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    Map<String, Object> json = new HashMap<>();
                    json.put("toggled", "false");
                    customResponse.setInfo(json);
                    return customResponse;
                }
            }else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.INVALID_CRITERIA);
                return customResponse;
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
    public CustomResponse getAllPosts(JSONObject body) {

        List<Post> posts = new ArrayList<>();
        posts = AllDBOperations.getAllPostsInUEM(body);

        JSONArray array = new JSONArray();
        for (Post post : posts){
            array.put(UtilsManager.postToJson(post));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Posts",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    @Override
    public CustomResponse getAllCourses() {
        List<Course> users = new ArrayList<>();
        users = AllDBOperations.getAllCoursesInUEM();

        JSONArray array = new JSONArray();
        for (Course univAdmin : users){
            array.put(UtilsManager.courseToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Students",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    @Override
    public CustomResponse getAllBatches() {
        List<Batch> users = new ArrayList<>();
        users = AllDBOperations.getAllBatchesInUEM();

        JSONArray array = new JSONArray();
        for (Batch univAdmin : users){
            array.put(UtilsManager.batchToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Students",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;
    }

    @Override
    public CustomResponse getAllBatches(JSONObject body) {

        try {
            if (body.has("BatchID") || body.has("AdminID") || body.has("CourseID")) {

                if (body.has("BatchID")) {
                    List<Batch> users = new ArrayList<>();
                    users = AllDBOperations.getAllBatchesInUEM_ByBatchID(body.getString("BatchID"));

                    Map<String, Object> map = new HashMap<>();
                    map.put("batches", users.toString());

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;

                } else if (body.has("AdminID")) {
                    List<Batch> users = new ArrayList<>();
                    users = AllDBOperations.getAllBatchesInUEM_ByAdminID(body.getString("AdminID"));

                    Map<String, Object> map = new HashMap<>();
                    map.put("batches", users.toString());

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    List<Batch> users = new ArrayList<>();
                    users = AllDBOperations.getAllBatchesInUEM_ByCourseID(body.getString("CourseID"));

                    Map<String, Object> map = new HashMap<>();
                    map.put("batches", users.toString());

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            } else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.INVALID_CRITERIA);
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
    public CustomResponse getAllCourses(JSONObject body) {

        try {
            if (body.has("Name") || body.has("CourseID") || body.has("CourseAdmin")) {

                if (body.has("Name")) {
                    List<Course> users = new ArrayList<>();
                    users = AllDBOperations.getAllCoursesInUEM(body.getString("Name"));

                    Map<String, Object> map = new HashMap<>();
                    map.put("courses", users.toString());

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;

                } else if (body.has("CourseID")) {
                    List<Course> users = new ArrayList<>();
                    users = AllDBOperations.getAllCoursesInUEMBID(body.getString("CourseID"));

                    Map<String, Object> map = new HashMap<>();
                    map.put("courses", users.toString());

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    List<Course> users = new ArrayList<>();
                    users = AllDBOperations.getAllCoursesInUEM_CourseAdmin(body.getString("CourseAdmin"));

                    Map<String, Object> map = new HashMap<>();
                    map.put("courses", users.toString());

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            } else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.INVALID_CRITERIA);
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
    public CustomResponse getUniversity(String UnivID) {
        List<University> users = new ArrayList<>();
        users = AllDBOperations.getAllUniversities_UnivID(UnivID);

        JSONArray array = new JSONArray();
        for (University univAdmin : users){
            array.put(UtilsManager.universityToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Universities",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;

    }

    @Override
    public CustomResponse getLevels_Filter(JSONObject body) {

        try {
            List<Level> modules = new ArrayList<>();
            modules = AllDBOperations.getAll_Levels_ModuleID(body.getString("ModuleID"));

            JSONArray array = new JSONArray();
            for (Level module : modules){
                if (body.has("Name")
                        && body.getString("Name").equalsIgnoreCase("none")){
                    array.put(UtilsManager.levelToJson(module));
                }
                if (body.has("Name")
                        && !body.getString("Name").equalsIgnoreCase("none")
                        && module.getName().toUpperCase().contains(body.getString("Name").toUpperCase())){
                    array.put(UtilsManager.levelToJson(module));
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("Levels",  array);

            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;

        }catch (Exception e){
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
    public CustomResponse getModules(JSONObject body) {

        try {
            List<Module> modules = new ArrayList<>();
            modules = AllDBOperations.getAll_Modules_UnivID(body.getString("UnivID"));

            JSONArray array = new JSONArray();
            for (Module module : modules){
                if (body.has("ModuleID")
                        && body.getString("ModuleID").equalsIgnoreCase("none")){
                    array.put(UtilsManager.moduleToJson(module));
                }
                if (body.has("ModuleID")
                        && !body.getString("ModuleID").equalsIgnoreCase("none")
                        && module.getName().toUpperCase().contains(body.getString("ModuleID").toUpperCase())){
                    array.put(UtilsManager.moduleToJson(module));
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("Modules",  array);

            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;

        }catch (Exception e){
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
    public CustomResponse getUniversity(JSONObject body) {

        try {
            List<University> universities = new ArrayList<>();
            universities = AllDBOperations.getAllUniversities_AdminID(body.getString("AdminID"));

            JSONArray array = new JSONArray();
            for (University university : universities){
                if (body.has("UnivID")
                        && body.getString("UnivID").equalsIgnoreCase("none")){
                    array.put(UtilsManager.universityToJson(university));
                }
                if (body.has("UnivID")
                        && !body.getString("UnivID").equalsIgnoreCase("none")
                        && university.getUnivID().toUpperCase().contains(body.getString("UnivID").toUpperCase())){
                    array.put(UtilsManager.universityToJson(university));
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("Universities",  array);

            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;

        }catch (Exception e){
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
    public CustomResponse getLevels_By_ModuleID(String UnivID) {
        List<Level> users = new ArrayList<>();
        users = AllDBOperations.getAll_Levels_ModuleID(UnivID);

        JSONArray array = new JSONArray();
        for (Level univAdmin : users){
            array.put(UtilsManager.levelToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Levels",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;

    }

    @Override
    public CustomResponse getLevels_By_LevelID(String UnivID) {
        List<Level> users = new ArrayList<>();
        users = AllDBOperations.getAll_Levels_ID(UnivID);

        JSONArray array = new JSONArray();
        for (Level univAdmin : users){
            array.put(UtilsManager.levelToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Levels",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;

    }

    @Override
    public CustomResponse getModules_By_ModuleID(String UnivID) {
        List<Module> users = new ArrayList<>();
        users = AllDBOperations.getAll_Modules_ModuleID(UnivID);

        JSONArray array = new JSONArray();
        for (Module univAdmin : users){
            array.put(UtilsManager.moduleToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Modules",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;

    }

    @Override
    public CustomResponse getModules(String UnivID) {
        List<Module> users = new ArrayList<>();
        users = AllDBOperations.getAll_Modules_UnivID(UnivID);

        JSONArray array = new JSONArray();
        for (Module univAdmin : users){
            array.put(UtilsManager.moduleToJson(univAdmin));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("Modules",  array);

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(true);
        customResponse.setMessage(Constants.SUCCESS);
        customResponse.setInfo(map);
        return customResponse;

    }


    // createModules
    @Override
    public CustomResponse createModules(JSONObject body) {

        try {
            if (body.has("UnivID") && body.has("Name")) {

                List<Module> modules = AllDBOperations.getAll_Modules_Name(body.getString("Name"));
                if (modules != null && modules.size() > 0){
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.ALREADY_EXIST);
                    return customResponse;
                }

                Map<String, Object> map = AllDBOperations.createModule(body);
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
                customResponse.setMessage(Constants.MODULE_CREATION_FAILURE);
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
    public CustomResponse createLevels(JSONObject body) {

        try {
            if (body.has("ModuleID") && body.has("UnivID") && body.has("Name")) {

                List<Level> modules = AllDBOperations.getAll_Levels_Name(body.getString("Name"));
                if (modules != null && modules.size() > 0){
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.ALREADY_EXIST);
                    return customResponse;
                }

                Map<String, Object> map = AllDBOperations.createLevel(body);
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
                customResponse.setMessage(Constants.LEVEL_CREATION_FAILURE);
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
    public CustomResponse createUniversity(JSONObject body) {

        try {
            if (body.has("UnivID") && body.has("AdminID")) {

                List<University> universities = AllDBOperations.getAllUniversities_UnivID(body.getString("UnivID"));
                if (universities != null && universities.size() > 0){
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.ALREADY_EXIST);
                    return customResponse;
                }

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
    public CustomResponse createEvent(JSONObject body) {

        try {
            if (body.has("text") && body.has("time")) {

                Map<String, Object> map = AllDBOperations.createEvent(body);
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
                customResponse.setMessage(Constants.POST_CREATION_FAILURE);
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
    public CustomResponse createPost(JSONObject body) {

        try {
            if (body.has("UserID") && body.has("text")) {

                Map<String, Object> map = AllDBOperations.createPost(body);
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
                customResponse.setMessage(Constants.POST_CREATION_FAILURE);
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
    public CustomResponse getAllConnection(JSONObject body) {

        try {
            List<Connection> connections = AllDBOperations.getAllConnectionsInUEM(body);
            Map<String, Object> map = new HashMap<>();
            JSONArray jsonArray = new JSONArray();
            for (Connection connection : connections){
                jsonArray.put(UtilsManager.connectionToJson(connection));
            }

            map.put("Connections", jsonArray);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;
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
    public CustomResponse searchAllConnection(JSONObject body) {

        try {
            List<User> users = AllDBOperations.searchAllConnectionsInUEM(body);
            Map<String, Object> map = new HashMap<>();
            JSONArray jsonArray = new JSONArray();
            for (User user : users){
                jsonArray.put(UtilsManager.userToJson(user));
            }

            map.put("Users", jsonArray);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;
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
    public CustomResponse getAllNotifications(JSONObject body) {

        try {
            List<Notification> notifications = AllDBOperations.getAllNotificationsInUEM(body);
            Map<String, Object> map = new HashMap<>();
            JSONArray jsonArray = new JSONArray();
            for (Notification notification : notifications){
                jsonArray.put(UtilsManager.notificationToJson(notification));
            }
            map.put("Notifications", jsonArray);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;
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
    public CustomResponse getAllEvents() {

        try {
            List<Event> events = AllDBOperations.getAllEventsInUEM();
            Map<String, Object> map = new HashMap<>();
            JSONArray jsonArray = new JSONArray();
            for (Event event : events){
                jsonArray.put(UtilsManager.eventToJson(event));
            }
            map.put("Events", jsonArray);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;
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
    public CustomResponse getAllMessengers(JSONObject body) {

        try {
            if (body.has("To")) {

                List<User> users = AllDBOperations.getAllMessengersInUEM(String.valueOf(body.get("To")));
                Map<String, Object> map = new HashMap<>();
                JSONArray jsonArray = new JSONArray();
                for (User user : users){
                    jsonArray.put(UtilsManager.userToJson(user));
                }
                map.put("Users", jsonArray);
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(true);
                customResponse.setMessage(Constants.SUCCESS);
                customResponse.setInfo(map);
                return customResponse;
            } else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.MESSAGE_SEARCH_FAILURE);
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

    // getAllMessages_ReadByMe
    @Override
    public CustomResponse getAllMessages_ReadByMe(JSONObject body) {

        try {
            List<Message> messages = AllDBOperations.getAllMessagesInUEM_Read_By_Me(
                    String.valueOf(body.get("To")),
                    String.valueOf(body.get("readByMe"))
            );
            Map<String, Object> map = new HashMap<>();
            JSONArray jsonArray = new JSONArray();
            for (Message message : messages){
                jsonArray.put(UtilsManager.messageToJson(message));
            }
            map.put("Messages", jsonArray);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setMessage(Constants.SUCCESS);
            customResponse.setInfo(map);
            return customResponse;
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
    public CustomResponse getAllMessages(JSONObject body) {

        try {
            if (body.has("readByUser")) {

                List<Message> messages = AllDBOperations.getAllMessagesInUEM(
                        String.valueOf(body.get("User1")),
                        String.valueOf(body.get("User2")),
                        String.valueOf(body.get("read")),
                        String.valueOf(body.get("readByUser"))
                        );
                Map<String, Object> map = new HashMap<>();
                JSONArray jsonArray = new JSONArray();
                for (Message message : messages){
                    jsonArray.put(UtilsManager.messageToJson(message));
                }
                map.put("Messages", jsonArray);
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(true);
                customResponse.setMessage(Constants.SUCCESS);
                customResponse.setInfo(map);
                return customResponse;
            }else if (body.has("User1") && body.has("User2")) {

                List<Message> messages = AllDBOperations.getAllMessagesInUEM(
                        String.valueOf(body.get("User1")),
                        String.valueOf(body.get("User2")),
                        String.valueOf(body.get("read"))
                        );
                Map<String, Object> map = new HashMap<>();
                JSONArray jsonArray = new JSONArray();
                for (Message message : messages){
                    jsonArray.put(UtilsManager.messageToJson(message));
                }
                map.put("Messages", jsonArray);
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(true);
                customResponse.setMessage(Constants.SUCCESS);
                customResponse.setInfo(map);
                return customResponse;
            } else if (body.has("To")) {

                List<Message> messages = AllDBOperations.getAllMessagesInUEM(
                        String.valueOf(body.get("To")),
                        String.valueOf(body.get("read"))
                );
                Map<String, Object> map = new HashMap<>();
                JSONArray jsonArray = new JSONArray();
                for (Message message : messages){
                    jsonArray.put(UtilsManager.messageToJson(message));
                }
                map.put("Messages", jsonArray);
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(true);
                customResponse.setMessage(Constants.SUCCESS);
                customResponse.setInfo(map);
                return customResponse;
            } else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.MESSAGE_SEARCH_FAILURE);
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
    public CustomResponse createConnection(JSONObject body) {

        try {
            if (body.has("From") && body.has("To") && body.has("status")) {

                if (body.getString("From").equalsIgnoreCase(body.getString("To"))){
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INVALID_CRITERIA);
                    return customResponse;
                }

                JSONObject searchBody = new JSONObject();
                searchBody.put("From", body.getString("From"));
                searchBody.put("To", body.getString("To"));

                List<Connection> connections = AllDBOperations.getAllConnectionsInUEM(searchBody);

                searchBody = new JSONObject();
                searchBody.put("From", body.getString("To"));
                searchBody.put("To", body.getString("From"));
                connections.addAll(AllDBOperations.getAllConnectionsInUEM(searchBody));

                if (connections.size() > 0){
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.ALREADY_EXIST);
                    return customResponse;
                }

                Map<String, Object> map = AllDBOperations.createConnection(body);
                if (map == null || Boolean.valueOf(String.valueOf(map.get("success"))) == false) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    JSONObject notificationBody = new JSONObject();
                    notificationBody.put("UserID", body.getString("To"));
                    notificationBody.put("text", "Connection Request Received: From : " + body.getString("From"));
                    createNotification(notificationBody);

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            } else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.CONNECTION_CREATION_FAILURE);
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
    public CustomResponse createNotification(JSONObject body) {

        try {
            if (body.has("UserID") && body.has("text")) {

                Map<String, Object> map = AllDBOperations.createNotification(body);
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
                customResponse.setMessage(Constants.NOTIFICATION_CREATION_FAILURE);
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
    public CustomResponse createMessage(JSONObject body) {

        try {
            if (body.has("From") && body.has("To") && body.has("text") && body.has("read")) {

                String MessageID = UtilsManager.generateUniqueID();
                body.put("MessageID", MessageID);

                Map<String, Object> map = AllDBOperations.createMessage(body);
                if (map == null || Boolean.valueOf(String.valueOf(map.get("success"))) == false) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {
                    JSONObject notificationBody = new JSONObject();
                    notificationBody.put("UserID", body.getString("To"));
                    notificationBody.put("text", "Message Received: From : " + body.getString("From") + ". Message: " + body.getString("text"));
                    createNotification(notificationBody);

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
            } else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.MESSAGE_CREATION_FAILURE);
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
    public CustomResponse createCourse(JSONObject body) {

        try {
            if (body.has("Name") && body.has("CourseAdmin")) {

                Map<String, Object> map = AllDBOperations.createCourse(body);
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
                customResponse.setMessage(Constants.COURSE_CREATION_FAILURE);
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
    public CustomResponse updateMessage(JSONObject body, Boolean append) {

        try {
            if (body.has("MessageID")){

                List<Message> messages = AllDBOperations.getAllMessagesInUEM(body.getString("MessageID"));
                if (messages== null || messages.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.POST_DOES_NOT_EXIST);
                    return customResponse;
                } else {

                    Map<String, Object> map = AllDBOperations.updateMessage(messages.get(0), body, append);
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
            }else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.INTERNAL_ERROR);
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
    public CustomResponse updatePost(JSONObject body, Boolean append) {

        try {

            List<Post> posts = AllDBOperations.getAllPostsInUEM(body);
            if (posts == null || posts.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.POST_DOES_NOT_EXIST);
                return customResponse;
            } else {

                Map<String, Object> map = AllDBOperations.updatePost(posts.get(0), body, append);
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

    public CustomResponse updateConnection(JSONObject body, Boolean append) {

        try {

            if (!body.has("ConnectionID")){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.CONNECTION_SEARCH_FAILURE);
                return customResponse;
            }

            JSONObject searchBody = new JSONObject();
            searchBody.put("ConnectionID", body.getString("ConnectionID"));
            List<Connection> connections = AllDBOperations.getAllConnectionsInUEM(searchBody);
            if (connections == null || connections.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.CONNECTION_DOES_NOT_EXIST);
                return customResponse;
            } else {
                Map<String, Object> map = AllDBOperations.updateConnections(connections.get(0), body, append);
                if (map == null || Boolean.valueOf(String.valueOf(map.get("success"))) == false) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.INTERNAL_ERROR);
                    customResponse.setInfo(map);
                    return customResponse;
                } else {

                    JSONObject notificationBody = new JSONObject();
                    notificationBody.put("UserID", connections.get(0).getFrom());
                    notificationBody.put("text", "Connection Request " + body.getString("status") + ": By : " + connections.get(0).getTo());
                    createNotification(notificationBody);

                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(true);
                    customResponse.setMessage(Constants.SUCCESS);
                    customResponse.setInfo(map);
                    return customResponse;
                }
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

    // updateNotifications_read_all
    @Override
    public CustomResponse updateNotifications_read_all(JSONObject body, Boolean append) {

        try {

            JSONObject searchBody = new JSONObject();
            searchBody.put("UserID", body.getString("User"));
            searchBody.put("read", "false");

            List<Notification> notifications = AllDBOperations.getAllNotificationsInUEM(searchBody);
            for (Notification notification : notifications){
                JSONObject updateBody = new JSONObject();
                updateBody.put("read", "true");
                AllDBOperations.updateNotifications(notification, updateBody, append);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("success", "true");
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(true);
            customResponse.setInfo(map);
            customResponse.setMessage(Constants.SUCCESS);
            return customResponse;
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
    public CustomResponse updateNotification(JSONObject body, Boolean append) {

        try {

            if (!body.has("NotificationID")){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.NOTIFICATION_SEARCH_FAILURE);
                return customResponse;
            }

            JSONObject searchBody = new JSONObject();
            searchBody.put("NotificationID", body.getString("NotificationID"));
            List<Notification> notifications = AllDBOperations.getAllNotificationsInUEM(searchBody);
            if (notifications == null || notifications.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.NOTIFICATION_DOES_NOT_EXIST);
                return customResponse;
            } else {

                Map<String, Object> map = AllDBOperations.updateNotifications(notifications.get(0), body, append);
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
    public CustomResponse updateUniversity_New(JSONObject body) {

        try {

            List<University> universities = AllDBOperations.getAllUniversities_UnivID(body.getString("UnivID"));
            if (universities == null || universities.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.UNIVERSITY_DOES_NOT_EXIST);
                return customResponse;
            } else {
                Map<String, Object> map = AllDBOperations.updateUniversity_New(universities.get(0), body);
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
    public CustomResponse updateLevel(JSONObject body) {

        try {

            List<Level> modules = AllDBOperations.getAll_Levels_ID(body.getString("LevelID"));
            if (modules == null || modules.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.LEVEL_DOES_NOT_EXIST);
                return customResponse;
            } else {
                Map<String, Object> map = AllDBOperations.updateLevel(modules.get(0), body);
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
    public CustomResponse updateModule(JSONObject body) {

        try {

            List<Module> modules = AllDBOperations.getAll_Modules_ModuleID(body.getString("ModuleID"));
            if (modules == null || modules.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.MODULE_DOES_NOT_EXIST);
                return customResponse;
            } else {
                Map<String, Object> map = AllDBOperations.updateModule(modules.get(0), body);
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
            if (universities == null || universities.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.UNIVERSITY_DOES_NOT_EXIST);
                return customResponse;
            } else {
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
    public CustomResponse updateAdmin(JSONObject body, Boolean append) {

        try {

            List<UnivAdmin> univAdmins = AllDBOperations.getAllAdmin_UemID(body.getString("adminID"));
            if (univAdmins == null || univAdmins.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.ADMIN_DOES_NOT_EXIST);
                return customResponse;
            } else {
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
    public CustomResponse updateStudent(JSONObject body, Boolean append) {

        try {

            List<Student> students = AllDBOperations.getAllStudents_UemID(body.getString("studentID"));
            if (students == null || students.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.STUDENT_DOES_NOT_EXIST);
                return customResponse;
            } else {
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
    public CustomResponse updateTeacher(JSONObject body, Boolean append) {

        try {

            List<Teacher> teachers = AllDBOperations.getAllTeachers_UemID(body.getString("teacherID"));
            if (teachers == null || teachers.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.TEACHER_DOES_NOT_EXIST);
                return customResponse;
            } else {
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
    public CustomResponse createBatch(JSONObject body) {

        try {
            if (body.has("Duration")
                    && body.has("SpanOver")
                    && body.has("Starting")
                    && body.has("Completion")
                    && body.has("Calendar")
                    && body.has("Billing")
                    && body.has("UnivID")
                    && body.has("CourseID")) {
                if (body.has("BatchRequests")) {

                    if (body.has("UEM_ID")) {
                        String UEM_ID = body.getString("UEM_ID");
                        List<Batch> batches = AllDBOperations.getAllBatchesInUEM_ByCourseID_UnivID_StateRequested_BatchRequestsExists(
                                body.getString("CourseID"),
                                body.getString("UnivID"));
                        Batch batch = batches != null && batches.size() > 0 ? batches.get(0) : null;
                        if (batch != null) {
                            List<Document> batchRequests = batch.getBatchRequests();
                            for (Document document : batchRequests) {
                                if (document.containsKey("UEM_ID")
                                        && UEM_ID.equalsIgnoreCase(document.getString("UEM_ID"))) {
                                    CustomResponse customResponse = new CustomResponse();
                                    customResponse.setSuccess(false);
                                    customResponse.setMessage(Constants.BATCH_REQUEST_ALREADY_RAISED);
                                    return customResponse;
                                }
                            }

                            Document document = new Document();
                            document.put("UEM_ID", UEM_ID);
                            batchRequests.add(document);

                            JSONArray jsonArray = UtilsManager.documentListToJsonArray(batchRequests);
                            JSONObject object = new JSONObject();
                            object.put("BatchRequests", jsonArray);

                            AllDBOperations.updateBatch(batch, object, false);

                            CustomResponse customResponse = new CustomResponse();
                            customResponse.setSuccess(true);
                            customResponse.setMessage(Constants.SUCCESS);
                            customResponse.setInfo(new HashMap<>());
                            return customResponse;
                        } else {

                            JSONArray array = new JSONArray();
                            JSONObject object = new JSONObject();
                            object.put("UEM_ID", UEM_ID);
                            array.put(object);

                            body.put("BatchRequests", array);

                            JSONObject status = new JSONObject();
                            status.put("status", "requested");
                            body.put("Status", status);

                            Map<String, Object> map = AllDBOperations.createBatch(body);
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
                    } else {
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.BATCH_CREATION_FAILURE);
                        return customResponse;
                    }
                } else {

                    if (!body.has("AdminID")){
                        CustomResponse customResponse = new CustomResponse();
                        customResponse.setSuccess(false);
                        customResponse.setMessage(Constants.BATCH_CREATION_FAILURE);
                        return customResponse;
                    }

                    Map<String, Object> map = AllDBOperations.createBatch(body);
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
            }else {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.BATCH_CREATION_FAILURE);
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
    public CustomResponse updateBatch(JSONObject body, Boolean append) {

        try {

            List<Batch> batches = AllDBOperations.getAllBatchesInUEM_ByBatchID(body.getString("batchID"));
            if (batches == null || batches.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.BATCH_DOES_NOT_EXIST);
                return customResponse;
            } else {
                Map<String, Object> map = AllDBOperations.updateBatch(batches.get(0), body, append);
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
    public CustomResponse deleteFromBatch(JSONObject body) {

        try {

            List<Batch> batches = AllDBOperations.getAllBatchesInUEM_ByBatchID(body.getString("batchID"));
            if (batches == null || batches.size() == 0) {
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(false);
                customResponse.setMessage(Constants.BATCH_DOES_NOT_EXIST);
                return customResponse;
            } else {
                Map<String, Object> map = AllDBOperations.deleteFromBatch(batches.get(0), body);
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


}
