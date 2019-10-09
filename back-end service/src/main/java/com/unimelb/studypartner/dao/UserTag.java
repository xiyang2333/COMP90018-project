package com.unimelb.studypartner.dao;

import java.io.Serializable;
import java.util.Date;

public class UserTag implements Serializable {
    private Integer userTagId;

    private Integer times;

    private Date lastSearchTime;

    private Integer tagId;

    private Integer userId;

    private String isValid;

    private static final long serialVersionUID = 1L;

    public Integer getUserTagId() {
        return userTagId;
    }

    public void setUserTagId(Integer userTagId) {
        this.userTagId = userTagId;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Date getLastSearchTime() {
        return lastSearchTime;
    }

    public void setLastSearchTime(Date lastSearchTime) {
        this.lastSearchTime = lastSearchTime;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userTagId=").append(userTagId);
        sb.append(", times=").append(times);
        sb.append(", lastSearchTime=").append(lastSearchTime);
        sb.append(", tagId=").append(tagId);
        sb.append(", userId=").append(userId);
        sb.append(", isValid=").append(isValid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}