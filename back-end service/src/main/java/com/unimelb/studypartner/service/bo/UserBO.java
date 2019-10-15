package com.unimelb.studypartner.service.bo;

/**
 * Created by xiyang on 2019/10/10
 */
public class UserBO {
    private int userId;
    private String userName;
    private String userPhoto;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
