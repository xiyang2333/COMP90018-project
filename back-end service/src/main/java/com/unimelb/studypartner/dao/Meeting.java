package com.unimelb.studypartner.dao;

import java.io.Serializable;
import java.util.Date;

public class Meeting implements Serializable {
    private Integer meetingId;

    private Integer userId;

    private String meetingLocation;

    private String meetingDescription;

    private Date meetingTime;

    private Date meetingCreateTime;

    private Integer classId;

    private Integer photoId;

    private static final long serialVersionUID = 1L;

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation == null ? null : meetingLocation.trim();
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription == null ? null : meetingDescription.trim();
    }

    public Date getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }

    public Date getMeetingCreateTime() {
        return meetingCreateTime;
    }

    public void setMeetingCreateTime(Date meetingCreateTime) {
        this.meetingCreateTime = meetingCreateTime;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", meetingId=").append(meetingId);
        sb.append(", userId=").append(userId);
        sb.append(", meetingLocation=").append(meetingLocation);
        sb.append(", meetingDescription=").append(meetingDescription);
        sb.append(", meetingTime=").append(meetingTime);
        sb.append(", meetingCreateTime=").append(meetingCreateTime);
        sb.append(", classId=").append(classId);
        sb.append(", photoId=").append(photoId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}