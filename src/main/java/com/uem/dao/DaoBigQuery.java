package com.uem.dao;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.uem.google.bigquery.main.AllBQOperations;
import com.uem.google.bigquery.main.BQTable_User;
import com.uem.model.CustomResponse;
import com.uem.model.User;
import com.uem.util.Constants;
import com.uem.util.GAuthenticate;
import com.uem.util.LogUtil;
import com.uem.util.UtilsManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Qualifier("DaoBigQuery")
public class DaoBigQuery implements DaoInterface{

    private static Logger logger = LogUtil.getInstance();

    public String test() {
        logger.debug("REQUEST_RECIEVED-DaoBigQuery");
        return "TEST-DaoBigQuery";
    }

    @Override
    public CustomResponse signUp(String email) {
        List<User> users = AllBQOperations.getAllUsers(email);
        if (users == null || users.size() == 0){
            Bigquery bigquery = GAuthenticate.getAuthenticated(true);

            ArrayList<TableDataInsertAllRequest.Rows> datachunk =
                    new ArrayList<TableDataInsertAllRequest.Rows>();
            TableDataInsertAllRequest.Rows row = new TableDataInsertAllRequest.Rows();
            Map<String, Object> data = new HashMap<>();
            data.put("UserID", UtilsManager.generateUniqueID());
            data.put("Email", email);
            data.put("Password", UtilsManager.generateUniqueID());
            row.setJson(data);
            datachunk.add(row);

            if (BQTable_User.insertDataRows(bigquery, datachunk)){
                CustomResponse customResponse = new CustomResponse();
                customResponse.setSuccess(true);
                customResponse.setInfo(data);
                customResponse.setMessage("Successful!");
                return customResponse;
            }
        }else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setSuccess(false);
            customResponse.setMessage("Either User with this email already exists or Password is Incorrect!");
            return customResponse;
        }

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(false);
        customResponse.setMessage(Constants.INTERNAL_ERROR);
        return customResponse;

    }

}
