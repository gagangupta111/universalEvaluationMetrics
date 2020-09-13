package com.uem.util;

import com.uem.model.Notification;
import com.uem.model.User;

import java.util.Comparator;

public class UsersComparatorByUpdatedAt implements Comparator<User> {

    public int compare(User o1,User o2){

        User s1=(User)o1;
        User s2=(User)o2;

        if(s1.get_updated_at()==s2.get_updated_at())
            return 0;
        else if(s1.get_updated_at().getTime() > s2.get_updated_at().getTime())
            return 1;
        else
            return -1;

    }

}