package com.uem.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public interface Constants {

    public static final String  GOOGLECREDENTIAL_AUTHORIZE = "GOOGLECREDENTIAL_AUTHORIZE";
    public static final String  INTERNAL_ERROR = "Oops, Something went wrong! please try again after some time! ";
    public static final String  FAILURE = "FAILURE";
    public static final String  SUCCESS = "SUCCESS";
    public static final String  LOGIN_FAILURE = "Either User does not exist or password is Incorrect!";
    public static final String  SIGN_UP_FAILURE = "User with this email already exists!";
    public static final String  NO_INFO_FOUND = "NO_INFO_FOUND";
    public static final String  AMAZON_S3_ERROR = "AMAZON_S3_ERROR";

    public static final String  UNIVERSITY_DOES_NOT_EXIST = "UNIVERSITY_DOES_NOT_EXIST";
    public static final String  POST_DOES_NOT_EXIST = "POST_DOES_NOT_EXIST";
    public static final String  ADMIN_DOES_NOT_EXIST = "ADMIN_DOES_NOT_EXIST";
    public static final String  STUDENT_DOES_NOT_EXIST = "STUDENT_DOES_NOT_EXIST";
    public static final String  TEACHER_DOES_NOT_EXIST = "TEACHER_DOES_NOT_EXIST";
    public static final String  COURSE_DOES_NOT_EXIST = "COURSE_DOES_NOT_EXIST";
    public static final String  BATCH_DOES_NOT_EXIST = "BATCH_DOES_NOT_EXIST";

    public static final String  INVALID_CRITERIA = "INVALID_CRITERIA";

    public static final String  ALREADY_EXIST = "ALREADY_EXIST";

    public static final String  UNIVERSITY_CREATION_FAILURE = " Name, Website and AdminID is required to create University!";
    public static final String  COURSE_CREATION_FAILURE = " Name and CourseAdmin is required to create Course!";
    public static final String  BATCH_CREATION_FAILURE = " Duration, SpanOver, Starting, Completion, Calendar, Billing, UnivID and CourseAdmin" +
            " is required to create Course OR Must be Requested Status with UEM_ID!";
    public static final String  BATCH_REQUEST_ALREADY_RAISED = "You have already raised one request in Past for this course";

    public static final String  POST_CREATION_FAILURE = " UserID and text is required to create Post!";
    public static final String  MESSAGE_CREATION_FAILURE = " From, To and text is required to create Message!";
    public static final String  MESSAGE_SEARCH_FAILURE = " User1 and User2 are required to find Messages!";

    public static final String  ADMIN_LOGIN_FAILURE = "Not An Admin";
    public static final String  STUDENT_LOGIN_FAILURE = "Not A Student";
    public static final String  TEACHER_LOGIN_FAILURE = "Not A Teacher";

    public static final Integer RETRY_ADACCOUNT_GETADACCOUNTS = 3;

    public static final String  ADMIN = "ADMIN";
    public static final String  TEACHER = "TEACHER";
    public static final String  STUDENT = "STUDENT";
    public static final String  COURSE_ADMIN = "COURSE_ADMIN";

}
