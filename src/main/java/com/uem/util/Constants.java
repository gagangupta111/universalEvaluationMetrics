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

    public static final String  UNIVERSITY_CREATION_FAILURE = " Name, Website and AdminID is required to create University!";

    public static final String  ADMIN_LOGIN_FAILURE = "Not An Admin";
    public static final String  STUDENT_LOGIN_FAILURE = "Not A Student";
    public static final String  TEACHER_LOGIN_FAILURE = "Not A Teacher";

    public static final Integer RETRY_ADACCOUNT_GETADACCOUNTS = 3;

}
