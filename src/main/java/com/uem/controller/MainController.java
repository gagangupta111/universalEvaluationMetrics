package com.uem.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.uem.model.CustomResponse;
import com.uem.model.User;
import com.uem.service.MainService;
import com.uem.util.AmazonS3Util;
import com.uem.util.Constants;
import com.uem.util.LogUtil;
import com.uem.util.UtilsManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/uem")
public class MainController {

    private static Logger logger = LogUtil.getInstance();

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<String> test(){

        logger.debug("REQUEST_RECIEVED-MainController");
        return ResponseEntity.ok()
                .header("key", "value")
                .body(mainService.test());

    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String>  signUp(@RequestBody String body) throws Exception{

        JSONObject jsonObject = new JSONObject(body.trim());
        logger.debug("REQUEST_RECIEVED-signUp");
        String email = jsonObject.getString("email");
        CustomResponse customResponse =  mainService.signUp(email);
        if (customResponse.getSuccess()){
            return ResponseEntity.ok()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getInfoAsJson().toString());
        }else {
            return ResponseEntity.badRequest()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        }
    }

    @PostMapping("/signin/{loginType}")
    @ResponseBody
    public ResponseEntity<String> signIn(@RequestBody String body, @PathVariable("loginType") String loginType) throws Exception{

        JSONObject jsonObject = new JSONObject(body.trim());
        logger.debug("REQUEST_RECIEVED-signUp");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        CustomResponse customResponse =  mainService.signIN(email, password, loginType.toUpperCase());
        if (customResponse.getSuccess()){
            return ResponseEntity.ok()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        }else {
            return ResponseEntity.badRequest()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        }
    }

    @PutMapping("/user/{userID}")
    @ResponseBody
    public ResponseEntity<String> updateUser(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,
            @RequestParam(value = "Email", required = false)String Email,
            @RequestParam(value = "Password", required = false) String Password,
            @RequestParam(value = "Name", required = false)String Name,
            @RequestParam(value = "Mobile", required = false) String Mobile,
            @RequestParam(value = "Address", required = false) String Address,
            @RequestParam(value = "DOB", required = false) String DOB,
            @RequestParam(value = "info", required = false) String info,

            @PathVariable("userID") String userID) throws Exception{

        try {
            JSONObject body = new JSONObject();

            body = Email != null ? body.put("Email", Email) : body;
            body = Password != null ? body.put("Password", Password) : body;
            body = Name != null ? body.put("Name", Name) : body;
            body = Mobile != null ? body.put("Mobile", Mobile) : body;
            body = Address != null ? body.put("Address", Address) : body;
            body = DOB != null ? body.put("DOB", DOB) : body;
            body = info != null ? body.put("info", info) : body;
            body = userID != null ? body.put("UserID", userID) : body;

            if (body.length() == 0){
                return ResponseEntity.badRequest()
                        .header("key", "value")
                        .body("NOTHING_TO_UPDATE");
            }

            if (Photo != null){

                String key_name = "USER_PHOTO_" + userID;
                File file = new File(key_name);
                FileUtils.writeByteArrayToFile(file, Photo.getBytes());

                PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                if (putObjectResult == null){
                    return ResponseEntity.badRequest()
                            .header("message", Constants.INTERNAL_ERROR)
                            .body(Constants.AMAZON_S3_ERROR);
                }
                JSONObject object = new JSONObject();
                object.put("ContentMd5", putObjectResult.getContentMd5());
                object.put("ETag", putObjectResult.getETag());
                object.put("ETag", putObjectResult.getETag());
                object.put("Name", key_name);
                body.put("Photo", object);
            }

            Boolean aBoolean =  mainService.updateUserInfo(body);
            if (aBoolean){
                return ResponseEntity.ok()
                        .header("key", "value")
                        .body(Constants.SUCCESS);
            }else {
                return ResponseEntity.badRequest()
                        .header("key", "value")
                        .body(Constants.FAILURE);
            }
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }

    }

    @GetMapping("/user/{userID}")
    @ResponseBody
    public ResponseEntity<String> getUserInfo(@PathVariable("userID") String userID) {

        List<User> users = mainService.getUserInfo(userID);
        if (users == null || users.size() == 0){
            return ResponseEntity.badRequest()
                    .header("message", "")
                    .body(Constants.FAILURE);
        }else {
            return ResponseEntity.ok()
                    .header("message", "")
                    .body(users.toString());
        }
    }

    @PostMapping("/University")
    @ResponseBody
    public ResponseEntity<String> createUniversity(@RequestBody String body) throws Exception{

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse =  mainService.createUniversity(jsonObject);
        if (customResponse.getSuccess()){
            return ResponseEntity.ok()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getInfo().toString());
        }else {
            return ResponseEntity.badRequest()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        }
    }

    @PostMapping("/University/{univID}")
    @ResponseBody
    public ResponseEntity<String> updateUniversity(@RequestBody String body, @PathVariable("univID") String univID) throws Exception{

        if (univID == null || univID.equals("")){
            return ResponseEntity.badRequest()
                    .header("message", "")
                    .body("");
        }
        JSONObject jsonObject = new JSONObject(body.trim());
        jsonObject.put("UnivID", univID);
        CustomResponse customResponse =  mainService.updateUniversity(jsonObject);
        if (customResponse.getSuccess()){
            return ResponseEntity.ok()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getInfo().toString());
        }else {
            return ResponseEntity.badRequest()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        }
    }
}
