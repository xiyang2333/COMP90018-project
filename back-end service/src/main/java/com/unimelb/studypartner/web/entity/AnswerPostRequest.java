package com.unimelb.studypartner.web.entity;

import java.util.List;

/**
 * Created by xiyang on 2019/10/3
 */
public class AnswerPostRequest {
    private int userId;
    private int postId;
    private String answer;
    private List<String> photoList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }
}
