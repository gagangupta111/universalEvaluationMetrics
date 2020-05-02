package com.uem.dao;

import com.uem.util.LogUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("DaoDummy")
public class DaoDummy implements DaoInterface{

    private static Logger logger = LogUtil.getInstance();

    public String test() {
        logger.debug("REQUEST_RECIEVED-DaoDummy");
        return "TEST-DaoDummy";
    }

}
