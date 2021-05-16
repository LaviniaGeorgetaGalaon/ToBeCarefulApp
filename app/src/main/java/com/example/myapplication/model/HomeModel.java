package com.example.myapplication.model;

public class HomeModel {

    private String userName, profileImage, textInfo,uid;

    public HomeModel() {
    }

    public HomeModel(String userName, String profileImage, String textInfo, String uid) {
        this.userName = userName;
        this.profileImage = profileImage;
        this.textInfo = textInfo;
        this.uid = uid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setTextInfo(String textImg) {
        this.textInfo = textImg;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public String getUid() {
        return uid;
    }
}
