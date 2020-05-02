package com.uem.dao;

import com.uem.util.LogUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("DaoBigQuery")
public class DaoBigQuery implements DaoInterface{

    private static Logger logger = LogUtil.getInstance();

    public String test() {
        logger.debug("REQUEST_RECIEVED-DaoBigQuery");
        return "TEST-DaoBigQuery";
    }

}
