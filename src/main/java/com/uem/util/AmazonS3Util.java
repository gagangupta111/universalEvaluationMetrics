package com.uem.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.google.api.client.util.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;

public class AmazonS3Util {

    private static final String ACCESS_KEY = "AKIAJ2K2ZRRPQFHWIBFA";
    private static final String SECRET_KEY = "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";
    private static final String BUCKET_NAME = "universalevaluationmetricslinkedin";
    public static final String ACCESS_URL = "https://universalevaluationmetricslinkedin.s3.ap-south-1.amazonaws.com/";

    private static AmazonS3 amazonS3 = null;
    private static Logger logger = LogUtil.getInstance();
    public static int retry = 3;

    public static AmazonS3 getS3Client(){
        if (amazonS3 == null){
            int retry = 3;
            while (retry >= 0){
                try {
                    AWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
                    amazonS3 = AmazonS3ClientBuilder.standard()
                            .withRegion(Regions.AP_SOUTH_1)
                            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                            .build();
                    return amazonS3;
                }catch (Exception e){
                    logger.debug(UtilsManager.exceptionAsString(e));
                    retry--;
                }
            }
        }else {
            return amazonS3;
        }
        return amazonS3;
    }

    public static PutObjectResult uploadFileInS3Bucket(String keyName, File file){

        while (retry >= 0){
            try {
                PutObjectResult putObjectResult =  getS3Client().putObject(new PutObjectRequest(BUCKET_NAME, keyName, file).withCannedAcl(CannedAccessControlList.PublicRead));
                URL url = getS3Client().getUrl(BUCKET_NAME, keyName);
                return putObjectResult;
            }catch (Exception e){
                logger.debug(UtilsManager.exceptionAsString(e));
                retry--;
            }
        }
        return null;
    }

    public static File getFileFromS3Bucket(String keyName){

        while (retry >= 0){
            try {
                S3Object s3Object = getS3Client().getObject(BUCKET_NAME, keyName);
                return s3ObjectToFile(s3Object, keyName);
            }catch (Exception e){
                logger.debug(UtilsManager.exceptionAsString(e));
                retry--;
            }
        }
        return null;
    }

    public static File s3ObjectToFile(S3Object s3Object, String keyName){

        File file = new File(keyName);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(s3Object.getObjectContent(), outputStream);
        } catch (Exception e) {
            logger.debug(UtilsManager.exceptionAsString(e));
            return null;
        }

        return file;
    }

}
