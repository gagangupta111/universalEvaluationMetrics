package com.uem.dao;

import com.uem.google.bigquery.main.AllBQOperations;
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
@Qualifier("DaoBigQuery")
public class DaoBigQuery implements DaoInterface {

    private static Logger logger = LogUtil.getInstance();

    public String test() {
        logger.debug("REQUEST_RECIEVED-DaoBigQuery");
        return "TEST-DaoBigQuery";
    }

    @Override
    public CustomResponse signUp(String email) {
        List<User> users = AllBQOperations.getAllUsers(email);
        if (users == null || users.size() == 0) {

            Map<String, Object> data = AllBQOperations.createUser(email);
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
        users = AllBQOperations.getAllUsers(email);

        if (users == null || users.size() == 0 || !password.equals(users.get(0).getPassword())) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage(Constants.LOGIN_FAILURE);
            return customResponse;
        }

        switch (loginType) {
            case "ADMIN":
                List<UnivAdmin> univAdmins = AllBQOperations.getAllAdmin(users.get(0).getUserID());
                if (univAdmins == null || univAdmins.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.ADMIN_LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            case "STUDENT":
                List<Student> students = AllBQOperations.getAllStudents(users.get(0).getUserID());
                if (students == null || students.size() == 0) {
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setSuccess(false);
                    customResponse.setMessage(Constants.STUDENT_LOGIN_FAILURE);
                    return customResponse;
                }
                break;
            case "TEACHER":
                List<Teacher> teachers = AllBQOperations.getAllTeachers(users.get(0).getUserID());
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
            while (keys.hasNext()){

                String key = keys.next();
                switch (key){
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

            return AllBQOperations.updateUser(user);

        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
            return false;
        }
    }

}
