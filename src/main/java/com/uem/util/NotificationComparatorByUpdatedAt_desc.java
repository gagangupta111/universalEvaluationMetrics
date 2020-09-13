package com.uem.util;

import com.uem.model.Connection;
import com.uem.model.Notification;
import org.mockito.internal.matchers.Not;

import java.util.Comparator;

public class NotificationComparatorByUpdatedAt_desc implements Comparator<Notification> {

    public int compare(Notification o1,Notification o2){

        Notification s1=(Notification)o1;
        Notification s2=(Notification)o2;

        if(s1.get_updated_at()==s2.get_updated_at())
            return 0;
        else if(s1.get_updated_at().getTime() > s2.get_updated_at().getTime())
            return -1;
        else
            return 1;

    }

}