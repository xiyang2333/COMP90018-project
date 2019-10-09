package com.example.service.entity;

import java.util.List;

/**
 * Created by xiyang on 2019/10/4
 */
public class UserPostResponse {
    private int responseStatus;
    private String errorMessage;

    private List<PostPart> postList;

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<PostPart> getPostList() {
        return postList;
    }

    public void setPostList(List<PostPart> postList) {
        this.postList = postList;
    }
}
