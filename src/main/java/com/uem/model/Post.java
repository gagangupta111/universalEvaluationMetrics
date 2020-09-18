package com.uem.model;

import org.bson.Document;

import java.util.Date;
import java.util.List;

public class Post {

    private String text;
    private String UserID;
    private String likes;
    private String shares;
    private String PostID;
    private List<Document> likesBy;
    private List<Document> sharesBy;

    private String objectID;
    private Date _created_at;
    private Date _updated_at;

    public List<Document> getLikesBy() {
        return likesBy;
    }

    public void setLikesBy(List<Document> likesBy) {
        this.likesBy = likesBy;
    }

    public List<Document> getSharesBy() {
        return sharesBy;
    }

    public void setSharesBy(List<Document> sharesBy) {
        this.sharesBy = sharesBy;
    }

    public String getObjectID() {
        return objectID;
    }
    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }
    public Date get_created_at() {
        return _created_at;
    }
    public void set_created_at(Date _created_at) {
        this._created_at = _created_at;
    }
    public Date get_updated_at() {
        return _updated_at;
    }
    public void set_updated_at(Date _updated_at) {
        this._updated_at = _updated_at;
    }

    @Override
    public String toString() {
        return "Post{" +
                "text='" + text + '\'' +
                ", UserID='" + UserID + '\'' +
                ", likes='" + likes + '\'' +
                ", shares='" + shares + '\'' +
                ", PostID='" + PostID + '\'' +
                ", objectID='" + objectID + '\'' +
                ", _created_at=" + _created_at +
                ", _updated_at=" + _updated_at +
                '}';
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
}
