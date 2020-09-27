package com.uem.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.uem.model.*;
import com.uem.service.MainService;
import com.uem.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

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

    @RequestMapping(value = "/test/string", method = RequestMethod.GET)
    public ResponseEntity<String> testStringArray() {

        JSONArray array = new JSONArray();
        array.put("one");
        array.put("two");

        logger.debug("REQUEST_RECIEVED-testStringArray");
            return ResponseEntity.ok()
                    .header("key", "value")
                    .body(array.toString());
    }

    @RequestMapping(value = "/test/json/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> testJson(@PathVariable("id") String id) {

        try {

            System.out.println("ID Value:" + id);

            JSONObject object = new JSONObject();
            object.put("key","value");
            logger.debug("REQUEST_RECIEVED-MainController");
            return ResponseEntity.ok()
                    .header("key", "value")
                    .body(object.toString());
        }catch (Exception e){
            JSONObject object = new JSONObject();
            logger.debug("REQUEST_RECIEVED-MainController");
            return ResponseEntity.badRequest()
                    .header("key", "value")
                    .body(mainService.test());
        }
    }

    @RequestMapping(value = "/deleteAll/{secretNumber}", method = RequestMethod.GET)
    public ResponseEntity<String> deleteAll(@PathVariable("secretNumber") String secretNumber) {

        if (secretNumber.equalsIgnoreCase("123456")){
            return ResponseEntity.ok()
                    .header("key", "value")
                    .body(mainService.deleteAll());
        }else {
            return ResponseEntity.badRequest()
                    .header("key", "value")
                    .body("Not Matching secretNumber");
        }
    }

    @RequestMapping(value = "/createDummy/{secretNumber}", method = RequestMethod.GET)
    public ResponseEntity<String> createDummy(@PathVariable("secretNumber") String secretNumber) {

        if (secretNumber.equalsIgnoreCase("123456")){
            return ResponseEntity.ok()
                    .header("key", "value")
                    .body(mainService.createDummy());
        }else {
            return ResponseEntity.badRequest()
                    .header("key", "value")
                    .body("Not Matching secretNumber");
        }
    }

    @GetMapping("/admin/{adminID}")
    @ResponseBody
    public ResponseEntity<String> geAdminInfo(@PathVariable("adminID") String adminID) {

        CustomResponse customResponse = mainService.geAdminInfo(adminID);
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

    @GetMapping("/course_admin/{adminID}")
    @ResponseBody
    public ResponseEntity<String> geCourseAdminInfo(@PathVariable("adminID") String adminID) {

        CustomResponse customResponse = mainService.geCourseAdminInfo(adminID);
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

    @GetMapping("/student/{studentID}")
    @ResponseBody
    public ResponseEntity<String> geStudentInfo(@PathVariable("studentID") String studentID) {

        CustomResponse customResponse = mainService.geStudentInfo(studentID);
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

    @GetMapping("/teacher/{teacherID}")
    @ResponseBody
    public ResponseEntity<String> geTeacherInfo(@PathVariable("teacherID") String teacherID) {

        CustomResponse customResponse = mainService.geTeacherInfo(teacherID);
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

    @GetMapping("/courses")
    @ResponseBody
    public ResponseEntity<String> getAllCourses() {

        CustomResponse customResponse = mainService.getAllCourses();
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
                ObjectMetadata objectMetadata = putObjectResult.getMetadata();
                object.put("Url", objectMetadata.getRawMetadataValue("url"));
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

    @GetMapping("/courses/search")
    @ResponseBody
    public ResponseEntity<String> getCoursesSearch(@RequestParam Map<String,String> allRequestParams) throws Exception {

        try {
            JSONObject body = new JSONObject();
            if (allRequestParams.containsKey("Name")){
                body.put("Name", String.valueOf(allRequestParams.get("Name")));
            }
            else if (allRequestParams.containsKey("CourseID")){
                body.put("CourseID", String.valueOf(allRequestParams.get("CourseID")));
            }else if (allRequestParams.containsKey("CourseAdmin")){
                body.put("CourseAdmin", String.valueOf(allRequestParams.get("CourseAdmin")));
            }else {
                return ResponseEntity.badRequest()
                        .header("message", "NoParamMentioned")
                        .body("NoParamMentioned");
            }
            CustomResponse customResponse = mainService.getAllCourses(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PostMapping("/courses")
    @ResponseBody
    public ResponseEntity<String> createCourse(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createCourse(jsonObject);
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
                        .body(customResponse.getInfoAsJson().toString());
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
                        .body(customResponse.getInfoAsJson().toString());
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
                        .body(customResponse.getInfoAsJson().toString());
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

    @PostMapping("/student/update/{studentID}/{append}")
    @ResponseBody
    public ResponseEntity<String> updateStudent(
            @RequestParam(value = "UserID", required = false) String UserID,
            @RequestParam(value = "UnivID", required = false) String UnivID,
            @RequestParam(value = "Batches", required = false) String Batches,
            @RequestParam(value = "info", required = false) String info,

            @PathVariable("studentID") String studentID,
            @PathVariable("append") Boolean append) throws Exception {

        try {
            if (studentID == null || studentID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body = UserID != null ? body.put("UserID", UserID.trim()) : body;
            body = UnivID != null ? body.put("UnivID", UnivID.trim()) : body;
            body = info != null ? body.put("info", new JSONObject(info.trim())) : body;
            body = Batches != null ? body.put("Batches", new JSONArray(Batches.trim())) : body;
            body.put("studentID", studentID);
            body.put("append", append);

            CustomResponse customResponse = mainService.updateStudent(body, append);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PostMapping("/student/Document/{studentID}/{append}")
    @ResponseBody
    public ResponseEntity<String> updateStudentDocument(
            @RequestParam(value = "Course", required = false) String Course,
            @RequestParam(value = "CourseDetails", required = false) String CourseDetails,
            @RequestParam(value = "Start", required = false) String Start,
            @RequestParam(value = "End", required = false) String End,
            @RequestParam(value = "Attachments", required = false) MultipartFile Attachments,

            @PathVariable("studentID") String studentID,
            @PathVariable("append") Boolean append) throws Exception {

        try {
            if (studentID == null || studentID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            JSONArray documents = new JSONArray();
            JSONObject document = new JSONObject();

            document = Course != null ? document.put("Course", Course.trim()) : document;
            document = CourseDetails != null ? document.put("CourseDetails", CourseDetails.trim()) : document;
            body = Start != null ? body.put("Start", Start.trim()) : body;
            body = End != null ? body.put("End", End.trim()) : body;
            body.put("studentID", studentID);
            body.put("append", append);

            if (Attachments != null) {
                MultipartFile multipartFile = Attachments;
                String key_name = "STUDENT_DOCUMENT_" + studentID + "_" + Math.random();
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
                document.put("Attachment", object);
                file.delete();
            }

            documents.put(document);
            body.put("Documents", documents);
            CustomResponse customResponse = mainService.updateStudent(body, append);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PostMapping("/student/Photo/{studentID}")
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
                        .body(customResponse.getInfoAsJson().toString());
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
                        .body(customResponse.getInfoAsJson().toString());
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
                        .body(customResponse.getInfoAsJson().toString());
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

    @GetMapping("/Batch")
    @ResponseBody
    public ResponseEntity<String> getAllBatches() {

        CustomResponse customResponse = mainService.getAllBatches();
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

    @GetMapping("/Batch/search")
    @ResponseBody
    public ResponseEntity<String> getBatchByID(@RequestParam Map<String,String> allRequestParams) throws Exception {

        try {
            JSONObject body = new JSONObject();
            if (allRequestParams.containsKey("BatchID")){
                body.put("BatchID", String.valueOf(allRequestParams.get("BatchID")));
            }
            else if (allRequestParams.containsKey("AdminID")){
                body.put("AdminID", String.valueOf(allRequestParams.get("AdminID")));
            }else if (allRequestParams.containsKey("CourseID")){
                body.put("CourseID", String.valueOf(allRequestParams.get("CourseID")));
            }else {
                return ResponseEntity.badRequest()
                        .header("message", "NoParamMentioned")
                        .body("NoParamMentioned");
            }
            CustomResponse customResponse = mainService.getAllBatches(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PostMapping("/Batch")
    @ResponseBody
    public ResponseEntity<String> createBatch(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createBatch(jsonObject);
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

    @PostMapping("/Batch/{batchID}/{append}")
    @ResponseBody
    public ResponseEntity<String> updateBatch(
            @RequestParam(value = "CourseID", required = false) String CourseID,
            @RequestParam(value = "Duration", required = false) String Duration,
            @RequestParam(value = "SpanOver", required = false) String SpanOver,
            @RequestParam(value = "Starting", required = false) String Starting,
            @RequestParam(value = "Completion", required = false) String Completion,
            @RequestParam(value = "LeadTutors", required = false) String LeadTutors,
            @RequestParam(value = "FellowTutors", required = false) String FellowTutors,
            @RequestParam(value = "Students", required = false) String Students,
            @RequestParam(value = "info", required = false) String info,
            @RequestParam(value = "Billing", required = false) String Billing,
            @RequestParam(value = "Calendar", required = false) String Calendar,
            @RequestParam(value = "AdminID", required = false) String AdminID,
            @RequestParam(value = "Status", required = false) String Status,

            @RequestParam(value = "Photo", required = false) MultipartFile Photo,

            @PathVariable("batchID") String batchID,
            @PathVariable("append") Boolean append) throws Exception {

        try {
            if (batchID == null || batchID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body = CourseID != null ? body.put("CourseID", CourseID.trim()) : body;
            body = Duration != null ? body.put("Duration", Duration.trim()) : body;
            body = SpanOver != null ? body.put("SpanOver", (SpanOver.trim())) : body;
            body = Starting != null ? body.put("Starting", (Starting.trim())) : body;
            body = Completion != null ? body.put("Completion", (Completion.trim())) : body;

            body = LeadTutors != null ? body.put("LeadTutors", new JSONArray(LeadTutors.trim())) : body;
            body = FellowTutors != null ? body.put("FellowTutors", new JSONArray(FellowTutors.trim())) : body;
            body = Students != null ? body.put("Students", new JSONArray(Students.trim())) : body;

            body = info != null ? body.put("info", info.trim()) : body;
            body = Billing != null ? body.put("Billing", new JSONObject(Billing.trim())) : body;
            body = Calendar != null ? body.put("Calendar", new JSONObject(Calendar.trim())) : body;
            body = AdminID != null ? body.put("AdminID", AdminID.trim()) : body;
            body = Status != null ? body.put("Status", new JSONObject(Status.trim())) : body;

            body.put("batchID", batchID);

            if (Photo != null) {

                String key_name = "BATCH_PHOTO_" + batchID;
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

            CustomResponse customResponse = mainService.updateBatch(body, append);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    // this will delete any object from array field, delete one key from object field
    @PostMapping("/Delete/Batch/{batchID}")
    @ResponseBody
    public ResponseEntity<String> deleteBatch(
            @RequestParam(value = "LeadTutors", required = false) String LeadTutors,
            @RequestParam(value = "FellowTutors", required = false) String FellowTutors,
            @RequestParam(value = "Students", required = false) String Students,
            @RequestParam(value = "info", required = false) String info,
            @RequestParam(value = "Billing", required = false) String Billing,
            @RequestParam(value = "Calendar", required = false) String Calendar,
            @RequestParam(value = "Status", required = false) String Status,

            @PathVariable("batchID") String batchID) throws Exception {

        try {
            if (batchID == null || batchID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();

            body = LeadTutors != null ? body.put("LeadTutors", (LeadTutors.trim())) : body;
            body = FellowTutors != null ? body.put("FellowTutors", (FellowTutors.trim())) : body;
            body = Students != null ? body.put("Students", (Students.trim())) : body;

            body = info != null ? body.put("info", info.trim()) : body;
            body = Billing != null ? body.put("Billing", (Billing.trim())) : body;
            body = Calendar != null ? body.put("Calendar", (Calendar.trim())) : body;
            body = Status != null ? body.put("Status", (Status.trim())) : body;

            body.put("batchID", batchID);

            CustomResponse customResponse = mainService.deleteFromBatch(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    // version 2 - LinkedIn App
    // all below api calls consider email as primary key.
    @PostMapping("/get/all/posts")
    @ResponseBody
    public ResponseEntity<String> getAllPosts(@RequestBody String body) throws Exception {

        if (body == null || (!body.contains("UserID") && !body.contains("PostID"))){

            return ResponseEntity.badRequest()
                    .header("message", "INVALID_CRITERION")
                    .body("INVALID_CRITERION");

        }

        JSONObject object = new JSONObject(body.trim());

        CustomResponse customResponse = mainService.getAllPosts(object);
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

    // this api call tell if a post is liked or shared by a user already or not
    @PostMapping("/get/all/postsActionBy")
    @ResponseBody
    public ResponseEntity<String> isPostToggledByUser(@RequestBody String body) throws Exception {

        if (body == null || (!body.contains("PostID") && !body.contains("UserID") && !body.contains("action"))){

            return ResponseEntity.badRequest()
                    .header("message", "INVALID_CRITERION")
                    .body("INVALID_CRITERION");

        }

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.isPostToggledByUser(jsonObject);

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

    @GetMapping("/all/posts")
    @ResponseBody
    public ResponseEntity<String> getAllPosts() throws Exception {

        JSONObject jsonObject = new JSONObject();
        CustomResponse customResponse = mainService.getAllPosts(jsonObject);
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

    @PostMapping("/all/posts")
    @ResponseBody
    public ResponseEntity<String> createPost(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createPost(jsonObject);
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

    @PutMapping("/all/posts")
    @ResponseBody
    public ResponseEntity<String> updatePost(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.updatePost(jsonObject);
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

    @GetMapping("/all/events")
    @ResponseBody
    public ResponseEntity<String> getAllEvents() throws Exception {

        CustomResponse customResponse = mainService.getAllEvents();
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

    @PostMapping("/events")
    @ResponseBody
    public ResponseEntity<String> createEvent(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createEvent(jsonObject);
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

    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<String> createMessage(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createMessage(jsonObject);
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

    @PutMapping("/messages")
    @ResponseBody
    public ResponseEntity<String> updateMessage(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.updateMessage(jsonObject);
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

    @PostMapping("/get/all/readByUser/messages")
    @ResponseBody
    public ResponseEntity<String> getAllMessages_ReadBy(
            @RequestBody String body) throws Exception {

        if (body == null || !body.contains("User1") || !body.contains("User2") || !body.contains("read") || !body.contains("readByUser")){

            return ResponseEntity.badRequest()
                    .header("message", "INVALID_CRITERION")
                    .body("INVALID_CRITERION");

        }

        JSONObject jsonObject = new JSONObject(body.trim());

        CustomResponse customResponse = mainService.getAllMessages(jsonObject);
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

    @PostMapping("/get/all/messages")
    @ResponseBody
    public ResponseEntity<String> getAllMessages(
            @RequestBody String body) throws Exception {

        if (body == null || !body.contains("User1") || !body.contains("User2") || !body.contains("read")){

            return ResponseEntity.badRequest()
                    .header("message", "INVALID_CRITERION")
                    .body("INVALID_CRITERION");

        }

        JSONObject jsonObject = new JSONObject(body.trim());

        CustomResponse customResponse = mainService.getAllMessages(jsonObject);
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

    @PostMapping("/all/messages/me")
    @ResponseBody
    public ResponseEntity<String> getAllMessages_ReadBy_Me(
            @RequestBody String body) throws Exception {

        if (body == null || !body.contains("To") || !body.contains("readByMe")){

            return ResponseEntity.badRequest()
                    .header("message", "INVALID_CRITERION")
                    .body("INVALID_CRITERION");

        }

        JSONObject jsonObject = new JSONObject(body.trim());

        CustomResponse customResponse = mainService.getAllMessages_ReadByMe(jsonObject);
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

    @PostMapping("/all/messages")
    @ResponseBody
    public ResponseEntity<String> getAllMessages_Read(
            @RequestBody String body) throws Exception {

        if (body == null || !body.contains("To") || !body.contains("read")){

            return ResponseEntity.badRequest()
                    .header("message", "INVALID_CRITERION")
                    .body("INVALID_CRITERION");

        }

        JSONObject jsonObject = new JSONObject(body.trim());

        CustomResponse customResponse = mainService.getAllMessages(jsonObject);
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

    @PostMapping("/all/messenger")
    @ResponseBody
    public ResponseEntity<String> getAllMessengers_To(
            @RequestBody String body) {

        try {

            if (body == null || !body.contains("To")){

                return ResponseEntity.badRequest()
                        .header("message", "INVALID_CRITERION")
                        .body("INVALID_CRITERION");

            }

            JSONObject jsonObject = new JSONObject(body.trim());
            CustomResponse customResponse = mainService.getAllMessengers(jsonObject);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        }catch (Exception e){
            return ResponseEntity.badRequest()
                    .header("message", "Exception")
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @PostMapping("/notifications")
    @ResponseBody
    public ResponseEntity<String> createNotification(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createNotification(jsonObject);
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

    @PutMapping("/notifications/readAll")
    @ResponseBody
    public ResponseEntity<String> updateNotification_read_all(@RequestBody String body) throws Exception {

        if (!body.contains("User")){
            return ResponseEntity.badRequest()
                    .header("message", "USER_IS_NEEDED")
                    .body("USER_IS_NEEDED");
        }

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.updateNotifications_read_all(jsonObject);
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

    @PutMapping("/notifications")
    @ResponseBody
    public ResponseEntity<String> updateNotification(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.updateNotification(jsonObject);
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

    @PostMapping("/all/notifications")
    @ResponseBody
    public ResponseEntity<String> getAllNotification(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.getAllNotifications(jsonObject);
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

    @PostMapping("/all/connections")
    @ResponseBody
    public ResponseEntity<String> getAllConnections(@RequestBody String body) throws Exception {

        Logs logs = new Logs();
        logs.setText("getAllConnections body : " + body != null ? body : "");
        logs.setLevel("info");
        logs.setFrom(this.getClass().getName());

        AllDBOperations.createLogs(logs);

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.getAllConnections(jsonObject);
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

    @PostMapping("/connections")
    @ResponseBody
    public ResponseEntity<String> createConnections(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createConnection(jsonObject);
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

    @PutMapping("/connections")
    @ResponseBody
    public ResponseEntity<String> updateConnections(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.updateConnection(jsonObject);
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

    @PostMapping("/all/search/connections")
    @ResponseBody
    public ResponseEntity<String> searchAllConnections(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.searchAllConnections(jsonObject);
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

    @PostMapping("/all/search/users")
    @ResponseBody
    public ResponseEntity<String> getUserInfo_By_Anything(@RequestBody String body) {

        try {
            if (body == null || !body.contains("key")){

                return ResponseEntity.badRequest()
                        .header("message", "INVALID_CRITERION")
                        .body("INVALID_CRITERION");

            }

            JSONObject object = new JSONObject(body.trim());
            CustomResponse customResponse = mainService.getAllUsers_By_Key(object.getString("key"));

            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        }catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @PostMapping("/all/users")
    @ResponseBody
    public ResponseEntity<String> getUserInfo_Email(@RequestBody String body) {

        try {
            if (body == null || !body.contains("email")){

                return ResponseEntity.badRequest()
                        .header("message", "INVALID_CRITERION")
                        .body("INVALID_CRITERION");

            }

            JSONObject object = new JSONObject(body.trim());
            CustomResponse customResponse = mainService.getUserInfo_Email(object.getString("email"));

            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        }catch (Exception e){
            logger.debug(UtilsManager.exceptionAsString(e));
            return ResponseEntity.badRequest()
                    .header("message", Constants.INTERNAL_ERROR)
                    .body(UtilsManager.exceptionAsString(e));
        }
    }

    @PostMapping("/signin2")
    @ResponseBody
    public ResponseEntity<String> signIn2(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        logger.debug("REQUEST_RECIEVED-signUp");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        CustomResponse customResponse = mainService.signIN2(email, password);
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

    @PostMapping("/signup2")
    @ResponseBody
    public ResponseEntity<String> signUp_2(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        logger.debug("REQUEST_RECIEVED-signUp");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");

        if ("none".equalsIgnoreCase(email)){
            return ResponseEntity.badRequest()
                    .header("message", "INVALID EMAIL")
                    .body("INVLID EMAIL");
        }

        CustomResponse customResponse = mainService.signUp_2(email, password);
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

    @PutMapping("/update/userOnlyPhoto")
    @ResponseBody
    public ResponseEntity<String> updateUserInfoPhoto(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,
            @RequestParam(value = "Email", required = true) String Email) throws Exception {

        try {

            if (Email == null || Email.length() == 0){

                return ResponseEntity.badRequest()
                        .header("message", "INVALID_CRITERION")
                        .body("INVALID_CRITERION");

            }

            JSONObject bodyObject = new JSONObject();
            bodyObject.put("Email", Email);

            List<User> users = AllDBOperations.getAllUsers_Email(bodyObject.getString("Email"));
            if (users == null || users.size() == 0){
                return ResponseEntity.badRequest()
                        .header("key", "value")
                        .body("EMAIL_DOES_NOT_EXIST");
            }

            if (Photo != null) {

                String key_name = "USER_PHOTO_" + bodyObject.getString("Email") + Photo.getOriginalFilename();
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
                ObjectMetadata objectMetadata = putObjectResult.getMetadata();
                object.put("Photo", AmazonS3Util.ACCESS_URL + key_name);
                object.put("Name", key_name);
                bodyObject.put("Photo", object);
                file.delete();
            }

            Boolean aBoolean = mainService.updateUser_Only_Photo(bodyObject);
            if (aBoolean) {
                return ResponseEntity.ok()
                        .header("message", Constants.SUCCESS)
                        .body(new JSONObject().toString());
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

    @PutMapping("/update/user")
    @ResponseBody
    public ResponseEntity<String> updateUserInfo(
            @RequestBody String body) throws Exception {

        try {

            if (body == null || !body.contains("Email")){

                return ResponseEntity.badRequest()
                        .header("message", "INVALID_CRITERION")
                        .body("INVALID_CRITERION");

            }

            JSONObject bodyObject = new JSONObject(body.trim());
            if (body.length() == 0) {
                return ResponseEntity.badRequest()
                        .header("key", "value")
                        .body("NOTHING_TO_UPDATE");
            }

            List<User> users = AllDBOperations.getAllUsers_Email(bodyObject.getString("Email"));
            if (users == null || users.size() == 0){
                return ResponseEntity.badRequest()
                        .header("key", "value")
                        .body("EMAIL_DOES_NOT_EXIST");
            }

            Boolean aBoolean = mainService.updateUserInfo_Email(bodyObject);
            if (aBoolean) {
                return ResponseEntity.ok()
                        .header("message", Constants.SUCCESS)
                        .body(new JSONObject().toString());
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

    // version 3
    // Updation and Sync Issues in Education. Curriculum, Syllabus, Teacher's Trainings and Examinations.

    @PostMapping("/signup/{type}")
    @ResponseBody
    public ResponseEntity<String> signUp(@RequestBody String body,  @PathVariable("type") String type) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        String email = jsonObject.has("email") ? jsonObject.getString("email") : null;
        String password = jsonObject.has("password") ? jsonObject.getString("password") : null;

        Set<String> set = new HashSet<>();
        set.add(Constants.ADMIN);
        set.add(Constants.STUDENT);
        set.add(Constants.TEACHER);
        set.add(Constants.COURSE_ADMIN);

        if (type == null || !set.contains(type.toUpperCase())){
            return ResponseEntity.badRequest()
                    .header("message", "INVALID_TYPE")
                    .body("INVALID_TYPE");
        }

        if (email == null || password == null){
            return ResponseEntity.badRequest()
                    .header("message", "Either Email or password is not provided!")
                    .body("Either Email or password is not provided!");
        }


        CustomResponse customResponse = mainService.signUp(email, password, type);
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

    @PostMapping("/signin/{type}")
    @ResponseBody
    public ResponseEntity<String> signIn(@RequestBody String body, @PathVariable("type") String type) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        String email = jsonObject.has("email") ? jsonObject.getString("email") : null;
        String password = jsonObject.has("password") ? jsonObject.getString("password") : null;

        Set<String> set = new HashSet<>();
        set.add(Constants.ADMIN);
        set.add(Constants.STUDENT);
        set.add(Constants.TEACHER);
        set.add(Constants.COURSE_ADMIN);

        if (type == null || !set.contains(type.toUpperCase())){
            return ResponseEntity.badRequest()
                    .header("message", "INVALID_TYPE")
                    .body("INVALID_TYPE");
        }

        if (email == null || password == null){
            return ResponseEntity.badRequest()
                    .header("message", "Either Email or password is not provided!")
                    .body("Either Email or password is not provided!");
        }

        CustomResponse customResponse = mainService.signIN(email, password, type.toUpperCase());
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

    @PostMapping("/userInfo")
    @ResponseBody
    public ResponseEntity<String> getUserInfo(@RequestBody String body) throws Exception{

        JSONObject jsonObject = new JSONObject(body.trim());
        String email = jsonObject.has("email") ? jsonObject.getString("email") : null;
        String type = jsonObject.has("type") ? jsonObject.getString("type") : null;

        Set<String> set = new HashSet<>();
        set.add(Constants.ADMIN);
        set.add(Constants.STUDENT);
        set.add(Constants.TEACHER);
        set.add(Constants.COURSE_ADMIN);

        if (type == null || !set.contains(type.toUpperCase())){
            return ResponseEntity.badRequest()
                    .header("message", "INVALID_TYPE")
                    .body("INVALID_TYPE");
        }

        if (email == null || type == null){
            return ResponseEntity.badRequest()
                    .header("message", "Either Email or type is not provided!")
                    .body("Either Email or type is not provided!");
        }

        CustomResponse customResponse = mainService.getUserInfo(email, type);
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

    @GetMapping("/all/user")
    @ResponseBody
    public ResponseEntity<String> getAllUsersInfo() {

        CustomResponse customResponse = mainService.getAllUserInfo();
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

    @PutMapping("/update/userOnlyPhoto/{type}")
    @ResponseBody
    public ResponseEntity<String> updateUserInfoPhoto_type(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,
            @RequestParam(value = "Email", required = true) String Email,
            @RequestParam(value = "type", required = true) String type) throws Exception {

        try {

            if (Email == null || Email.length() == 0 || type == null || type.length() == 0){

                return ResponseEntity.badRequest()
                        .header("message", "INVALID_CRITERION")
                        .body("INVALID_CRITERION");

            }

            Set<String> set = new HashSet<>();
            set.add(Constants.ADMIN);
            set.add(Constants.STUDENT);
            set.add(Constants.TEACHER);
            set.add(Constants.COURSE_ADMIN);

            if (type == null || !set.contains(type.toUpperCase())){
                return ResponseEntity.badRequest()
                        .header("message", "INVALID_TYPE")
                        .body("INVALID_TYPE");
            }

            JSONObject bodyObject = new JSONObject();
            bodyObject.put("Email", Email);
            bodyObject.put("type", type);

            if (Photo != null) {

                String key_name = "USER_PHOTO_" + bodyObject.getString("Email") + Photo.getOriginalFilename();
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
                ObjectMetadata objectMetadata = putObjectResult.getMetadata();
                object.put("Photo", AmazonS3Util.ACCESS_URL + key_name);
                object.put("Name", key_name);
                bodyObject.put("Photo", object);
                file.delete();
            }

            Boolean aBoolean = mainService.updateUser_Only_Photo_Type(bodyObject);
            if (aBoolean) {
                return ResponseEntity.ok()
                        .header("message", Constants.SUCCESS)
                        .body(new JSONObject().toString());
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

    @PutMapping("/update/user/{type}")
    @ResponseBody
    public ResponseEntity<String> updateUserInfo_type(
            @RequestBody String body) throws Exception {

        try {

            if (body == null || !body.contains("Email")){

                return ResponseEntity.badRequest()
                        .header("message", "INVALID_CRITERION")
                        .body("INVALID_CRITERION");

            }

            JSONObject bodyObject = new JSONObject(body.trim());

            Set<String> set = new HashSet<>();
            set.add(Constants.ADMIN);
            set.add(Constants.STUDENT);
            set.add(Constants.TEACHER);
            set.add(Constants.COURSE_ADMIN);

            if (!bodyObject.has("type") || !set.contains(bodyObject.getString("type") .toUpperCase())){
                return ResponseEntity.badRequest()
                        .header("message", "INVALID_TYPE")
                        .body("INVALID_TYPE");
            }

            Boolean aBoolean = mainService.updateUserInfo_Email_type(bodyObject);
            if (aBoolean) {
                return ResponseEntity.ok()
                        .header("message", Constants.SUCCESS)
                        .body(new JSONObject().toString());
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

    @PutMapping("/update/universityOnlyPhoto/{univID}")
    @ResponseBody
    public ResponseEntity<String> updateUniversity_New_Photo_Only(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,
            @PathVariable("univID") String univID) throws Exception {

        try {
            if (univID == null || univID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();
            body.put("UnivID", univID);

            if (Photo != null) {

                String key_name = "UNIVERSITY_PHOTO_" + univID + "_" + Photo.getOriginalFilename();;
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
                ObjectMetadata objectMetadata = putObjectResult.getMetadata();
                object.put("Photo", AmazonS3Util.ACCESS_URL + key_name);
                object.put("Name", key_name);
                body.put("Photo", object);
                file.delete();
            }

            CustomResponse customResponse = mainService.updateUniversity_New(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PutMapping("/update/university/{univID}")
    @ResponseBody
    public ResponseEntity<String> updateUniversity_New(
            @RequestBody String bodyString,
            @PathVariable("univID") String univID) throws Exception {

        try {
            if (univID == null || univID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject(bodyString.trim());
            body.put("UnivID", univID);

            CustomResponse customResponse = mainService.updateUniversity_New(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PostMapping("/University")
    @ResponseBody
    public ResponseEntity<String> createUniversity(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createUniversity(jsonObject);
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

    @PostMapping("/Levels/search")
    @ResponseBody
    public ResponseEntity<String> get_Levels_Input_Filter(@RequestBody String body) throws Exception {

        JSONObject object = new JSONObject(body.trim());
        if (object.has("ModuleID") && object.has("Name") ){
            CustomResponse customResponse = mainService.getLevels_Filter(object);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        }else {
            return ResponseEntity.badRequest()
                    .header("message", "ModuleID, Name is required!")
                    .body("AdminID, UnivID required!");
        }
    }

    @PostMapping("/Modules/search")
    @ResponseBody
    public ResponseEntity<String> get_Modules_Input_Filter(@RequestBody String body) throws Exception {

        JSONObject object = new JSONObject(body.trim());
        if (object.has("ModuleID") && object.has("UnivID") ){
            CustomResponse customResponse = mainService.getModules(object);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        }else {
            return ResponseEntity.badRequest()
                    .header("message", "AdminID, UnivID required!")
                    .body("AdminID, UnivID required!");
        }
    }

    @PostMapping("/University/search")
    @ResponseBody
    public ResponseEntity<String> getUniversity_Users_Filter(@RequestBody String body) throws Exception {

        JSONObject object = new JSONObject(body.trim());
        if (object.has("AdminID") && object.has("UnivID") ){
            CustomResponse customResponse = mainService.getUniversity(object);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
            } else {
                return ResponseEntity.badRequest()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getMessage());
            }
        }else {
            return ResponseEntity.badRequest()
                    .header("message", "AdminID, UnivID required!")
                    .body("AdminID, UnivID required!");
        }
    }

    @GetMapping("/University/{univID}")
    @ResponseBody
    public ResponseEntity<String> getUniversity(@PathVariable("univID") String univID) throws Exception {

        CustomResponse customResponse = mainService.getUniversity(univID);
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

    @GetMapping("/get/Levels/{LevelID}")
    @ResponseBody
    public ResponseEntity<String> get_Levels_By_ID(@PathVariable("LevelID") String LevelID) throws Exception {

        CustomResponse customResponse = mainService.get_Levels_By_LevelID(LevelID);
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

    @GetMapping("/get/Modules/{ModuleID}")
    @ResponseBody
    public ResponseEntity<String> getModules_By_ID(@PathVariable("ModuleID") String ModuleID) throws Exception {

        CustomResponse customResponse = mainService.getModules_By_ModuleID(ModuleID);
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

    @GetMapping("/levels/{ModuleID}")
    @ResponseBody
    public ResponseEntity<String> getLevels_By_Module_ID(@PathVariable("ModuleID") String ModuleID) throws Exception {

        CustomResponse customResponse = mainService.getlevels_By_ModuleID(ModuleID);
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

    @GetMapping("/Modules/{UnivID}")
    @ResponseBody
    public ResponseEntity<String> getModules(@PathVariable("UnivID") String univID) throws Exception {

        CustomResponse customResponse = mainService.getModules(univID);
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

    @PostMapping("/Modules")
    @ResponseBody
    public ResponseEntity<String> createModules(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createModules(jsonObject);
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

    @PutMapping("/update/module/{ModuleID}")
    @ResponseBody
    public ResponseEntity<String> update_Module(
            @RequestBody String bodyString,
            @PathVariable("ModuleID") String ModuleID) throws Exception {

        try {
            if (ModuleID == null || ModuleID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject(bodyString.trim());
            body.put("ModuleID", ModuleID);

            CustomResponse customResponse = mainService.update_module(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PutMapping("/update/modulePhotoOnly/{ModuleID}")
    @ResponseBody
    public ResponseEntity<String> update_Module(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,
            @PathVariable("ModuleID") String ModuleID) throws Exception {

        try {
            if (ModuleID == null || ModuleID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();
            body.put("ModuleID", ModuleID);

            if (Photo != null) {

                String key_name = "MODULE_PHOTO_" + ModuleID + "_" + Photo.getOriginalFilename();;
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
                ObjectMetadata objectMetadata = putObjectResult.getMetadata();
                object.put("Photo", AmazonS3Util.ACCESS_URL + key_name);
                object.put("Name", key_name);
                body.put("Photo", object);
                file.delete();
            }

            CustomResponse customResponse = mainService.update_module(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PostMapping("/Levels")
    @ResponseBody
    public ResponseEntity<String> create_Levels(@RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body.trim());
        CustomResponse customResponse = mainService.createLevels(jsonObject);
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

    @PutMapping("/update/levelImages/{LevelID}")
    @ResponseBody
    public ResponseEntity<String> update_Level_Images(
            @RequestParam(value = "Image", required = false) MultipartFile Image,
            @RequestParam(value = "Name", required = false) String Name,
            @PathVariable("LevelID") String LevelID) throws Exception {

        try {
            if (LevelID == null || LevelID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();
            body.put("LevelID", LevelID);
            body.put("Name", Name);

            if (Image != null) {

                String key_name = "LEVEL_IMAGE" + LevelID + "_" + Image.getOriginalFilename();;
                File file = new File(key_name);
                FileUtils.writeByteArrayToFile(file, Image.getBytes());

                PutObjectResult putObjectResult = AmazonS3Util.uploadFileInS3Bucket(key_name, file);
                if (putObjectResult == null) {
                    return ResponseEntity.badRequest()
                            .header("message", Constants.INTERNAL_ERROR)
                            .body(Constants.AMAZON_S3_ERROR);
                }
                JSONObject object = new JSONObject();
                object.put("ContentMd5", putObjectResult.getContentMd5());
                object.put("ETag", putObjectResult.getETag());
                ObjectMetadata objectMetadata = putObjectResult.getMetadata();
                object.put("Photo", AmazonS3Util.ACCESS_URL + key_name);
                object.put("Name", key_name);
                body.put("Image", object);
                file.delete();
            }

            CustomResponse customResponse = mainService.update_level(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PutMapping("/update/levelPhotoOnly/{LevelID}")
    @ResponseBody
    public ResponseEntity<String> update_Level_Photo_Only(
            @RequestParam(value = "Photo", required = false) MultipartFile Photo,
            @PathVariable("LevelID") String LevelID) throws Exception {

        try {
            if (LevelID == null || LevelID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject();
            body.put("LevelID", LevelID);

            if (Photo != null) {

                String key_name = "LEVEL_PHOTO" + LevelID + "_" + Photo.getOriginalFilename();;
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
                ObjectMetadata objectMetadata = putObjectResult.getMetadata();
                object.put("Photo", AmazonS3Util.ACCESS_URL + key_name);
                object.put("Name", key_name);
                body.put("Photo", object);
                file.delete();
            }

            CustomResponse customResponse = mainService.update_level(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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

    @PutMapping("/update/level/{LevelID}")
    @ResponseBody
    public ResponseEntity<String> update_Level_Info(
            @RequestBody String bodyString,
            @PathVariable("LevelID") String LevelID) throws Exception {

        try {
            if (LevelID == null || LevelID.equals("")) {
                return ResponseEntity.badRequest()
                        .header("message", "")
                        .body("");
            }
            JSONObject body = new JSONObject(bodyString.trim());
            body.put("LevelID", LevelID);

            CustomResponse customResponse = mainService.update_level(body);
            if (customResponse.getSuccess()) {
                return ResponseEntity.ok()
                        .header("message", customResponse.getMessage())
                        .body(customResponse.getInfoAsJson().toString());
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
