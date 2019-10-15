package com.unimelb.studypartner.service.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by xiyang on 2019/10/10
 */
public class ActivityBO {
    private Integer activityId;
    private Integer userId;
    private String activityName;
    private String activityLocation;
    private String activityDescription;
    private Date time;
    private Integer tagId;
    private String picture;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<UserBO> userBOList;
    private UserBO createUser;
    private int participantId;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public List<UserBO> getUserBOList() {
        return userBOList;
    }

    public void setUserBOList(List<UserBO> userBOList) {
        this.userBOList = userBOList;
    }

    public UserBO getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserBO createUser) {
        this.createUser = createUser;
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }
}
