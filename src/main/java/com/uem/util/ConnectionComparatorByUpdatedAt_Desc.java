package com.uem.util;

import com.uem.model.Connection;
import com.uem.model.Message;

import java.util.Comparator;

public class ConnectionComparatorByUpdatedAt_Desc implements Comparator<Connection> {

    public int compare(Connection o1,Connection o2){

        Connection s1=(Connection)o1;
        Connection s2=(Connection)o2;

        if(s1.get_updated_at()==s2.get_updated_at())
            return 0;
        else if(s1.get_updated_at().getTime() > s2.get_updated_at().getTime())
            return 1;
        else
            return -1;

    }

}