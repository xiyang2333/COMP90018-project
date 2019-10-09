package com.example.service.entity;

import java.util.List;

/**
 * Created by xiyang on 2019/10/3
 */
public class UpdateTagRequest {
    private int userId;
    private List<Integer> userTags;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getUserTags() {
        return userTags;
    }

    public void setUserTags(List<Integer> userTags) {
        this.userTags = userTags;
    }
}
