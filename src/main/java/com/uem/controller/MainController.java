package com.uem.controller;

import com.uem.model.CustomResponse;
import com.uem.model.User;
import com.uem.service.MainService;
import com.uem.util.Constants;
import com.uem.util.LogUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> updateUser(@RequestBody String body, @PathVariable("userID") String userID) throws Exception{

        JSONObject jsonObject = new JSONObject(body.trim());

        if (jsonObject.length() == 0){
            return ResponseEntity.badRequest()
                    .header("key", "value")
                    .body("NOTHING_TO_UPDATE");
        }

        jsonObject.put("UserID", userID);
        Boolean aBoolean =  mainService.updateUserInfo(jsonObject);
        if (aBoolean){
            return ResponseEntity.ok()
                    .header("key", "value")
                    .body(Constants.SUCCESS);
        }else {
            return ResponseEntity.badRequest()
                    .header("key", "value")
                    .body(Constants.FAILURE);
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
}
