package com.uem.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.uem.model.*;
import com.uem.service.MainService;
import com.uem.util.AmazonS3Util;
import com.uem.util.Constants;
import com.uem.util.LogUtil;
import com.uem.util.UtilsManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/uem")
public class MainController {

    private static Logger logger = LogUtil.getInstance();

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<String> test() {

        logger.debug("REQUEST_RECIEVED-MainController");
        return ResponseEntity.ok()
                .header("key", "value")
                .body(mainService.test());

    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signUp(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        logger.debug("REQUEST_RECIEVED-signUp");
        String email = jsonObject.getString("email");
        CustomResponse customResponse = mainService.signUp(email);
        if (customResponse.getSuccess()) {
            return ResponseEntity.ok()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getInfoAsJson().toString());
        } else {
            return ResponseEntity.badRequest()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        }
    }

    @PostMapping("/signin/{loginType}")
    @ResponseBody
    public ResponseEntity<String> signIn(@RequestBody String body, @PathVariable("loginType") String loginType) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        logger.debug("REQUEST_RECIEVED-signUp");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        CustomResponse customResponse = mainService.signIN(email, password, loginType.toUpperCase());
        if (customResponse.getSuccess()) {
            return ResponseEntity.ok()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        } else {
            return ResponseEntity.badRequest()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        }
    }

    @PutMapping("/user/{userID}")
    @ResponseBody
    public ResponseEntity<String> updateUser(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,
            @RequestParam(value = "Email", required = false) String Email,
            @RequestParam(value = "Password", required = false) String Password,
            @RequestParam(value = "Name", required = false) String Name,
            @RequestParam(value = "Mobile", required = false) String Mobile,
            @RequestParam(value = "Address", required = false) String Address,
            @RequestParam(value = "DOB", required = false) String DOB,
            @RequestParam(value = "info", required = false) String info,

            @PathVariable("userID") String userID) throws Exception {

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

            if (body.length() == 0) {
                return ResponseEntity.badRequest()
                        .header("key", "value")
                        .body("NOTHING_TO_UPDATE");
            }

            if (Photo != null) {

                String key_name = "USER_PHOTO_" + userID;
                File file = new File(key_name);
                FileUtils.writeByteArrayToFile(file, Photo.getBytes());

                PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                if (putObjectResult == null) {
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
                file.delete();
            }

            Boolean aBoolean = mainService.updateUserInfo(body);
            if (aBoolean) {
                return ResponseEntity.ok()
                        .header("key", "value")
                        .body(Constants.SUCCESS);
            } else {
                return ResponseEntity.badRequest()
                        .header("key", "value")
                        .body(Constants.FAILURE);
            }
        } catch (Exception e) {
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
        if (users == null || users.size() == 0) {
            return ResponseEntity.badRequest()
                    .header("message", "")
                    .body(Constants.FAILURE);
        } else {
            return ResponseEntity.ok()
                    .header("message", "")
                    .body(users.toString());
        }
    }

    @PostMapping("/University")
    @ResponseBody
    public ResponseEntity<String> createUniversity(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createUniversity(jsonObject);
        if (customResponse.getSuccess()) {
            return ResponseEntity.ok()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getInfo().toString());
        } else {
            return ResponseEntity.badRequest()
                    .header("message", customResponse.getMessage())
                    .body(customResponse.getMessage());
        }
    }

    @PostMapping("/University/{univID}/{append}")
    @ResponseBody
    public ResponseEntity<String> updateUniversity(
            @RequestParam(value = "Name", required = false) String Name,
            @RequestParam(value = "Started", required = false) String Started,
            @RequestParam(value = "UnivAdmins", required = false) String UnivAdmins,
            @RequestParam(value = "Students", required = false) String Students,
            @RequestParam(value = "Teachers", required = false) String Teachers,
            @RequestParam(value = "Courses", required = false) String Courses,
            @RequestParam(value = "Website", required = false) String Website,
            @RequestParam(value = "LegalInfo", required = false) String LegalInfo,
            @RequestParam(value = "MoreInfo", required = false) String MoreInfo,
            @RequestParam(value = "info", required = false) String info,
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,

            @PathVariable("univID") String univID,
            @PathVariable("append") Boolean append) throws Exception {

        try {
            if (univID == null || univID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body = Name != null ? body.put("Name", Name.trim()) : body;
            body = Started != null ? body.put("Started", Started.trim()) : body;
            body = UnivAdmins != null ? body.put("UnivAdmins", new JSONArray(UnivAdmins.trim())) : body;
            body = Students != null ? body.put("Students", new JSONArray(Students.trim())) : body;
            body = Teachers != null ? body.put("Teachers", new JSONArray(Teachers.trim())) : body;
            body = Courses != null ? body.put("Courses", new JSONArray(Courses.trim())) : body;
            body = UnivAdmins != null ? body.put("UnivAdmins", new JSONArray(UnivAdmins.trim())) : body;
            body = Website != null ? body.put("Website", Website.trim()) : body;
            body = LegalInfo != null ? body.put("LegalInfo", new JSONObject(LegalInfo.trim())) : body;
            body = MoreInfo != null ? body.put("MoreInfo", new JSONObject(MoreInfo.trim())) : body;
            body = info != null ? body.put("info", info.trim()) : body;
            body.put("UnivID", univID);

            if (Photo != null) {

                String key_name = "UNIVERSITY_PHOTO_" + univID;
                File file = new File(key_name);
                FileUtils.writeByteArrayToFile(file, Photo.getBytes());

                PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                if (putObjectResult == null) {
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
                file.delete();
            }

            CustomResponse customResponse = mainService.updateUniversity(body, append);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfo().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @GetMapping("/admin/{adminID}")
    @ResponseBody
    public ResponseEntity<String> geAdminInfo(@PathVariable("adminID") String adminID) {

        List<UnivAdmin> users = mainService.geAdminInfo(adminID);
        if (users == null || users.size() == 0) {
            return ResponseEntity.badRequest()
                    .header("message", "")
                    .body(Constants.FAILURE);
        } else {
            return ResponseEntity.ok()
                    .header("message", "")
                    .body(users.toString());
        }
    }

    @GetMapping("/student/{studentID}")
    @ResponseBody
    public ResponseEntity<String> geStudentInfo(@PathVariable("studentID") String studentID) {

        List<Student> users = mainService.geStudentInfo(studentID);
        if (users == null || users.size() == 0) {
            return ResponseEntity.badRequest()
                    .header("message", "")
                    .body(Constants.FAILURE);
        } else {
            return ResponseEntity.ok()
                    .header("message", "")
                    .body(users.toString());
        }
    }

    @GetMapping("/teacher/{teacherID}")
    @ResponseBody
    public ResponseEntity<String> geTeacherInfo(@PathVariable("teacherID") String teacherID) {

        List<Teacher> users = mainService.geTeacherInfo(teacherID);
        if (users == null || users.size() == 0) {
            return ResponseEntity.badRequest()
                    .header("message", "")
                    .body(Constants.FAILURE);
        } else {
            return ResponseEntity.ok()
                    .header("message", "")
                    .body(users.toString());
        }
    }

    @PostMapping("/admin/{adminID}/Document/{append}")
    @ResponseBody
    public ResponseEntity<String> updateAdminDocument(
            @RequestParam(value = "Course", required = false) String Course,
            @RequestParam(value = "CourseDetails", required = false) String CourseDetails,
            @RequestParam(value = "Start", required = false) String Start,
            @RequestParam(value = "End", required = false) String End,
            @RequestParam(value = "Attachments", required = false) MultipartFile[] Attachments,

            @PathVariable("adminID") String adminID,
            @PathVariable("append") Boolean append) throws Exception {

        try {
            if (adminID == null || adminID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body = Course != null ? body.put("Course", Course.trim()) : body;
            body = CourseDetails != null ? body.put("CourseDetails", CourseDetails.trim()) : body;
            body = Start != null ? body.put("Start", Start.trim()) : body;
            body = End != null ? body.put("End", End.trim()) : body;
            body.put("adminID", adminID);
            body.put("append", append);

            if (Attachments != null) {
                JSONArray attachmentsArray = new JSONArray();
                for (int i = 0; i < Attachments.length; i++){
                    MultipartFile multipartFile = Attachments[i];
                    String key_name = "ADMIN_ATTACHMENTS_" + Course + "_" + CourseDetails + "_" + i;
                    File file = new File(key_name);
                    FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());

                    PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                    if (putObjectResult == null) {
                        return ResponseEntity.badRequest()
                                .header("message", Constants.INTERNAL_ERROR)
                                .body(Constants.AMAZON_S3_ERROR);
                    }
                    JSONObject object = new JSONObject();
                    object.put("ContentMd5", putObjectResult.getContentMd5());
                    object.put("ETag", putObjectResult.getETag());
                    object.put("Name", key_name);
                    attachmentsArray.put(object);
                    file.delete();
                }
                body.put("Documents", attachmentsArray);
            }

            CustomResponse customResponse = mainService.updateAdmin(body, append);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfo().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @PostMapping("/admin/{adminID}/Photo")
    @ResponseBody
    public ResponseEntity<String> updateAdminPhoto(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,

            @PathVariable("adminID") String adminID) throws Exception {

        try {
            if (adminID == null || adminID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body.put("adminID", adminID);

            if (Photo != null) {
                MultipartFile multipartFile = Photo;
                String key_name = "ADMIN_PHOTO";
                File file = new File(key_name);
                FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());

                PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                if (putObjectResult == null) {
                    return ResponseEntity.badRequest()
                            .header("message", Constants.INTERNAL_ERROR)
                            .body(Constants.AMAZON_S3_ERROR);
                }
                JSONObject object = new JSONObject();
                object.put("ContentMd5", putObjectResult.getContentMd5());
                object.put("ETag", putObjectResult.getETag());
                object.put("Name", key_name);
                body.put("Photo", object);
                file.delete();
            }

            CustomResponse customResponse = mainService.updateAdmin(body, false);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfo().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @PostMapping("/student/{studentID}/Document/{append}")
    @ResponseBody
    public ResponseEntity<String> updateStudentDocument(
            @RequestParam(value = "Course", required = false) String Course,
            @RequestParam(value = "CourseDetails", required = false) String CourseDetails,
            @RequestParam(value = "Start", required = false) String Start,
            @RequestParam(value = "End", required = false) String End,
            @RequestParam(value = "Attachments", required = false) MultipartFile[] Attachments,

            @PathVariable("studentID") String studentID,
            @PathVariable("append") Boolean append) throws Exception {

        try {
            if (studentID == null || studentID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body = Course != null ? body.put("Course", Course.trim()) : body;
            body = CourseDetails != null ? body.put("CourseDetails", CourseDetails.trim()) : body;
            body = Start != null ? body.put("Start", Start.trim()) : body;
            body = End != null ? body.put("End", End.trim()) : body;
            body.put("studentID", studentID);
            body.put("append", append);

            if (Attachments != null) {
                JSONArray attachmentsArray = new JSONArray();
                for (int i = 0; i < Attachments.length; i++){
                    MultipartFile multipartFile = Attachments[i];
                    String key_name = "ADMIN_ATTACHMENTS_" + Course + "_" + CourseDetails + "_" + i;
                    File file = new File(key_name);
                    FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());

                    PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                    if (putObjectResult == null) {
                        return ResponseEntity.badRequest()
                                .header("message", Constants.INTERNAL_ERROR)
                                .body(Constants.AMAZON_S3_ERROR);
                    }
                    JSONObject object = new JSONObject();
                    object.put("ContentMd5", putObjectResult.getContentMd5());
                    object.put("ETag", putObjectResult.getETag());
                    object.put("Name", key_name);
                    attachmentsArray.put(object);
                    file.delete();
                }
                body.put("Documents", attachmentsArray);
            }

            CustomResponse customResponse = mainService.updateStudent(body, append);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfo().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @PostMapping("/student/{studentID}/Photo")
    @ResponseBody
    public ResponseEntity<String> updateStudentPhoto(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,

            @PathVariable("studentID") String studentID) throws Exception {

        try {
            if (studentID == null || studentID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body.put("studentID", studentID);

            if (Photo != null) {
                MultipartFile multipartFile = Photo;
                String key_name = "STUDENT_PHOTO_" + studentID;
                File file = new File(key_name);
                FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());

                PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                if (putObjectResult == null) {
                    return ResponseEntity.badRequest()
                            .header("message", Constants.INTERNAL_ERROR)
                            .body(Constants.AMAZON_S3_ERROR);
                }
                JSONObject object = new JSONObject();
                object.put("ContentMd5", putObjectResult.getContentMd5());
                object.put("ETag", putObjectResult.getETag());
                object.put("Name", key_name);
                body.put("Photo", object);
                file.delete();
            }

            CustomResponse customResponse = mainService.updateStudent(body, false);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfo().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @PostMapping("/teacher/{teacherID}/Document/{append}")
    @ResponseBody
    public ResponseEntity<String> updateTeacherDocument(
            @RequestParam(value = "Course", required = false) String Course,
            @RequestParam(value = "CourseDetails", required = false) String CourseDetails,
            @RequestParam(value = "Start", required = false) String Start,
            @RequestParam(value = "End", required = false) String End,
            @RequestParam(value = "Attachments", required = false) MultipartFile[] Attachments,

            @PathVariable("teacherID") String teacherID,
            @PathVariable("append") Boolean append) throws Exception {

        try {
            if (teacherID == null || teacherID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body = Course != null ? body.put("Course", Course.trim()) : body;
            body = CourseDetails != null ? body.put("CourseDetails", CourseDetails.trim()) : body;
            body = Start != null ? body.put("Start", Start.trim()) : body;
            body = End != null ? body.put("End", End.trim()) : body;
            body.put("teacherID", teacherID);
            body.put("append", append);

            if (Attachments != null) {
                JSONArray attachmentsArray = new JSONArray();
                for (int i = 0; i < Attachments.length; i++){
                    MultipartFile multipartFile = Attachments[i];
                    String key_name = "ADMIN_ATTACHMENTS_" + Course + "_" + CourseDetails + "_" + i;
                    File file = new File(key_name);
                    FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());

                    PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                    if (putObjectResult == null) {
                        return ResponseEntity.badRequest()
                                .header("message", Constants.INTERNAL_ERROR)
                                .body(Constants.AMAZON_S3_ERROR);
                    }
                    JSONObject object = new JSONObject();
                    object.put("ContentMd5", putObjectResult.getContentMd5());
                    object.put("ETag", putObjectResult.getETag());
                    object.put("Name", key_name);
                    attachmentsArray.put(object);
                    file.delete();
                }
                body.put("Documents", attachmentsArray);
            }

            CustomResponse customResponse = mainService.updateTeacher(body, append);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfo().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @PostMapping("/teacher/{teacherID}/Photo")
    @ResponseBody
    public ResponseEntity<String> updateTeacherPhoto(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,

            @PathVariable("teacherID") String teacherID) throws Exception {

        try {
            if (teacherID == null || teacherID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body.put("teacherID", teacherID);

            if (Photo != null) {
                MultipartFile multipartFile = Photo;
                String key_name = "TEACHER_PHOTO_" + teacherID;
                File file = new File(key_name);
                FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());

                PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                if (putObjectResult == null) {
                    return ResponseEntity.badRequest()
                            .header("message", Constants.INTERNAL_ERROR)
                            .body(Constants.AMAZON_S3_ERROR);
                }
                JSONObject object = new JSONObject();
                object.put("ContentMd5", putObjectResult.getContentMd5());
                object.put("ETag", putObjectResult.getETag());
                object.put("Name", key_name);
                body.put("Photo", object);
                file.delete();
            }

            CustomResponse customResponse = mainService.updateTeacher(body, false);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfo().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }


}
