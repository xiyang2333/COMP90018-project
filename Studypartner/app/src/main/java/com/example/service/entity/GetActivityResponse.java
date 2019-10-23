package com.example.service.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by xiyang on 2019/10/9
 */
public class GetActivityResponse {
    private int responseStatus;
    private String errorMessage;

    private String activityName;
    private String activityLocation;
    private String activityDescription;
    private Date time;
    private int tagId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<UserPart> userList;
    private UserPart createUser;
    private int participantId;
    private String picture;

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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
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

    public List<UserPart> getUserList() {
        return userList;
    }

    public void setUserList(List<UserPart> userList) {
        this.userList = userList;
    }

    public UserPart getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserPart createUser) {
        this.createUser = createUser;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
