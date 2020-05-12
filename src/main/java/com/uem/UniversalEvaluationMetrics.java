package com.uem;

import com.uem.util.LogUtil;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class UniversalEvaluationMetrics {

    private static Logger logger = LogUtil.getInstance();

    public static void main(String[] args) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        logger.debug("Started-Start");
        SpringApplication.run(UniversalEvaluationMetrics.class, args);
        logger.debug("Started-End");
    }

}
