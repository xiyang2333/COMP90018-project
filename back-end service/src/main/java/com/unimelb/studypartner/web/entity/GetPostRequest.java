package com.unimelb.studypartner.web.entity;

/**
 * Created by xiyang on 2019/10/9
 */
public class GetPostRequest {
    private int postId;
    private int userId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
