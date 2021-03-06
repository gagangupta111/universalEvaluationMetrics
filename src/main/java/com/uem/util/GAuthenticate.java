package com.uem.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;
import org.apache.log4j.Logger;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class GAuthenticate {

	public static String GOOGLEAPIS_OAUTH2_TOKEN = "";
	public static final HttpTransport TRANSPORT = new NetHttpTransport();
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static Logger logger = LogUtil.getInstance();
	// universalevaluationmetrics-73d9d22181cb.json
	// "UniversalEvaluationMetrics-b94a43ef7a72.json";
	public static final String BIG_QUERY_JSON_FILE = "UniversalEvaluationMetrics-b94a43ef7a72.json";
	public static Bigquery bigquery = null;

	public static Bigquery getAuthenticated(boolean refresh){

		if (refresh){
			try {
				ClassLoader classloader = Thread.currentThread().getContextClassLoader();
				InputStream inputStream = classloader.getResourceAsStream(BIG_QUERY_JSON_FILE);
				InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader);
				for (String line; (line = reader.readLine()) != null;) {
					GOOGLEAPIS_OAUTH2_TOKEN += line;
				}

				if((bigquery = createAuthorizedClient()) != null){
					return bigquery;
				}
				else{
					return null;
				}
			}catch (Exception e){
				logger.debug(UtilsManager.exceptionAsString(e));
				return null;
			}
		}else {
			return bigquery;
		}


	}
	
	public static Bigquery createAuthorizedClient(){

		GoogleCredential credential = null;
		
		if((credential = authorize()) != null){
			return new Bigquery.Builder(TRANSPORT, JSON_FACTORY, credential).build();
		}
		else{
			return null;
		}
		
	}

	public static GoogleCredential authorize(){

		try{

			GoogleCredential credential = GoogleCredential.fromStream(
					new ByteArrayInputStream(GOOGLEAPIS_OAUTH2_TOKEN.getBytes(StandardCharsets.UTF_8)))
					.createScoped(BigqueryScopes.all());

			return credential;

		}
		catch(Exception e){
			RollbarManager.sendExceptionOnRollBar(Constants.GOOGLECREDENTIAL_AUTHORIZE, e);
			logger.error("Exception : Authenticate - Authorize Method" );
			logger.error(e);
			return null;
		}

	}
}