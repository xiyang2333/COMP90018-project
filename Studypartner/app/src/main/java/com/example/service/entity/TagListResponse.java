package com.example.service.entity;



import java.util.List;

/**
 * Created by xiyang on 2019/9/17
 */
public class TagListResponse {

    private int responseStatus;
    private String errorMessage;

    private List<Tag> tagList;

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

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
}
