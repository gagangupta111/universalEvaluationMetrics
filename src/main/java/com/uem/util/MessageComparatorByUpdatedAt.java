package com.uem.util;

import com.uem.model.Message;

import java.util.Comparator;

public class MessageComparatorByUpdatedAt implements Comparator<Message> {

    public int compare(Message o1,Message o2){

        Message s1=(Message)o1;
        Message s2=(Message)o2;

        if(s1.get_updated_at()==s2.get_updated_at())
            return 0;
        else if(s1.get_updated_at().getTime() > s2.get_updated_at().getTime())
            return 1;
        else
            return -1;

    }

}