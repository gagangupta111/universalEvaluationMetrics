package com.uem.controller;

import com.uem.model.CustomResponse;
import com.uem.model.UnivAdmin;
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
                    .header("key", "value")
                    .body(customResponse.getInfoAsJson().toString());
        }else {
            return ResponseEntity.badRequest()
                    .header("key", "value")
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
                    .header("key", "value")
                    .body(customResponse.getMessage());
        }else {
            return ResponseEntity.badRequest()
                    .header("key", "value")
                    .body(customResponse.getMessage());
        }
    }

    @PostMapping("/user/{userID}")
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

    @GetMapping("/admin/{progress}")
    @ResponseBody
    public List<UnivAdmin> getAdminsInfo(@PathVariable("progress") String progress) {

        return null;

    }

}
