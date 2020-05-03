package com.uem.controller;

import com.google.api.client.json.Json;
import com.uem.model.CustomResponse;
import com.uem.service.MainService;
import com.uem.util.LogUtil;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
