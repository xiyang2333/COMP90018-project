package com.unimelb.studypartner.web.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by xiyang on 2019/10/9
 */
public class GetPostResponse {
    private int responseStatus;
    private String errorMessage;

    private String postName;
    private String postDescription;
    private int tagId;
    private List<String> photoList;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<AnswerPart> answerList;

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

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public List<AnswerPart> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerPart> answerList) {
        this.answerList = answerList;
    }
}
