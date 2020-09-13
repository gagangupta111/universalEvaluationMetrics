package com.uem.util;

import com.uem.model.Connection;
import com.uem.model.Post;

import java.util.Comparator;

public class PostComparatorByUpdatedAt_desc implements Comparator<Post> {

    public int compare(Post o1,Post o2){

        Post s1=(Post)o1;
        Post s2=(Post)o2;

        if(s1.get_updated_at()==s2.get_updated_at())
            return 0;
        else if(s1.get_updated_at().getTime() > s2.get_updated_at().getTime())
            return -1;
        else
            return 1;

    }

}