package com.uem.util;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.ConfigBuilder;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class RollbarManager {

	static Logger logger = LogUtil.getInstance();
	public final static String ROLLBAR_ACCESS_TOKEN = "549b43f4894b47f8a45d8359acb39855";
	public final static Boolean LOCAL_LOGGONG = true;

	public static void sendExceptionOnRollBar(String message, Exception e) {

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();

		Map<String, Object> map = new HashMap<>();
		map.put("message", e.getMessage());
		map.put("trace", exceptionAsString);
		final Rollbar rollbar = new Rollbar(ConfigBuilder.withAccessToken(ROLLBAR_ACCESS_TOKEN)
				.environment("production").handleUncaughtErrors(true).build());
		if (LOCAL_LOGGONG){
			logger.debug(message);
			logger.debug(map);
		}else {
			rollbar.error(message, map);
		}
	}

	public static void sendExceptionOnRollBar(String message, Map<String, Object> map) {

		final Rollbar rollbar = new Rollbar(ConfigBuilder.withAccessToken(ROLLBAR_ACCESS_TOKEN)
				.environment("production").handleUncaughtErrors(true).build());
		if (LOCAL_LOGGONG){
			logger.debug(message);
			logger.debug(map);
		}else {
			rollbar.error(message, map);
		}

	}

	public static void sendExceptionOnRollBar(String message, String response, Map<String, Object> entries) {

		Map<String, Object> map = new HashMap<>();
		map.put("response", response);
		map.putAll(entries);
		final Rollbar rollbar = new Rollbar(ConfigBuilder.withAccessToken(ROLLBAR_ACCESS_TOKEN)
				.environment("production").handleUncaughtErrors(true).build());
		if (LOCAL_LOGGONG){
			logger.debug(message);
			logger.debug(map);
		}else {
			rollbar.error(message, map);
		}

	}

	public static void sendExceptionOnRollBar(String message, Exception e, Map<String, Object> entries) {

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();

		Map<String, Object> map = new HashMap<>();
		map.put("message", e.getMessage());
		map.put("trace", exceptionAsString);
		map.putAll(entries);
		final Rollbar rollbar = new Rollbar(ConfigBuilder.withAccessToken(ROLLBAR_ACCESS_TOKEN)
				.environment("production").handleUncaughtErrors(true).build());
		if (LOCAL_LOGGONG){
			logger.debug(message);
			logger.debug(map);
		}else {
			rollbar.error(message, map);
		}

	}

	public static void sendExceptionOnRollBar(String message, String error) {

		final Rollbar rollbar = new Rollbar(ConfigBuilder.withAccessToken(ROLLBAR_ACCESS_TOKEN)
				.environment("production").handleUncaughtErrors(true).build());
		Map<String, Object> map = new HashMap<>();
		map.put("message", "[" + error + "]");
		if (LOCAL_LOGGONG){
			logger.debug(message);
			logger.debug(map);
		}else {
			rollbar.error(message, map);
		}


	}

	public static void INFO(String message, Map<String, Object> map) {

		final Rollbar rollbar = new Rollbar(ConfigBuilder.withAccessToken(ROLLBAR_ACCESS_TOKEN)
				.environment("production").handleUncaughtErrors(true).build());
		if (LOCAL_LOGGONG){
			logger.debug(message);
			logger.debug(map);
		}else {
			rollbar.error(message, map);
		}


	}

	public static void sendMessageOnRollBar(String message) {
		
		final Rollbar rollbar = new Rollbar(ConfigBuilder.withAccessToken(ROLLBAR_ACCESS_TOKEN)
				.environment("production").handleUncaughtErrors(true).build());
		if (LOCAL_LOGGONG){
			logger.debug(message);
		}else {
			rollbar.error(message);
		}


	}
}