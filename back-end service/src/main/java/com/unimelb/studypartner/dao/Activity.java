package com.unimelb.studypartner.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Activity implements Serializable {
    private Integer activityId;

    private Integer userId;

    private String activityName;

    private String activityLocation;

    private String activityDescription;

    private Date activityTime;

    private Date activityCreateTime;

    private Integer tagId;

    private String activityPhoto;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private static final long serialVersionUID = 1L;

    // base64 photo String
    private String activityPic;

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
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation == null ? null : activityLocation.trim();
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription == null ? null : activityDescription.trim();
    }

    public Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }

    public Date getActivityCreateTime() {
        return activityCreateTime;
    }

    public void setActivityCreateTime(Date activityCreateTime) {
        this.activityCreateTime = activityCreateTime;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getActivityPhoto() {
        return activityPhoto;
    }

    public void setActivityPhoto(String activityPhoto) {
        this.activityPhoto = activityPhoto == null ? null : activityPhoto.trim();
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

    public String getActivityPic() {
        return activityPic;
    }

    public void setActivityPic(String activityPic) {
        this.activityPic = activityPic;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", activityId=").append(activityId);
        sb.append(", userId=").append(userId);
        sb.append(", activityName=").append(activityName);
        sb.append(", activityLocation=").append(activityLocation);
        sb.append(", activityDescription=").append(activityDescription);
        sb.append(", activityTime=").append(activityTime);
        sb.append(", activityCreateTime=").append(activityCreateTime);
        sb.append(", tagId=").append(tagId);
        sb.append(", activityPhoto=").append(activityPhoto);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}