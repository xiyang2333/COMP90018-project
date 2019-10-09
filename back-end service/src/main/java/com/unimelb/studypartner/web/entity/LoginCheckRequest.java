package com.unimelb.studypartner.web.entity;

/**
 * Created by xiyang on 2019/9/10
 */
public class LoginCheckRequest {
    private String userLoginName;
    private String userEmail;
    private String userPassword;

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
