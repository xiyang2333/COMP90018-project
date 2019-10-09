package com.unimelb.studypartner.service.bo;

/**
 * Created by xiyang on 2019/10/2
 */
public class RegisterBO {
    private int userId;
    private boolean isNewUser;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean newUser) {
        isNewUser = newUser;
    }
}
