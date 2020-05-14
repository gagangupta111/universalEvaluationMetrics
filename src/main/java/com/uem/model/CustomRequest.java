package com.uem.model;

import org.springframework.web.multipart.MultipartFile;

public class CustomRequest {

    private MultipartFile Photo;
    private String Mobile;
    private String Name;

    public MultipartFile getPhoto() {
        return Photo;
    }

    public void setPhoto(MultipartFile photo) {
        Photo = photo;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
