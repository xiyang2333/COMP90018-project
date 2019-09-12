package com.unimelb.studypartner.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Post implements Serializable {
    private Integer postId;

    private String postType;

    private String postDescription;

    private Integer postPrice;

    private Date postDatetime;

    private Integer userId;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private static final long serialVersionUID = 1L;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType == null ? null : postType.trim();
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription == null ? null : postDescription.trim();
    }

    public Integer getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(Integer postPrice) {
        this.postPrice = postPrice;
    }

    public Date getPostDatetime() {
        return postDatetime;
    }

    public void setPostDatetime(Date postDatetime) {
        this.postDatetime = postDatetime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", postId=").append(postId);
        sb.append(", postType=").append(postType);
        sb.append(", postDescription=").append(postDescription);
        sb.append(", postPrice=").append(postPrice);
        sb.append(", postDatetime=").append(postDatetime);
        sb.append(", userId=").append(userId);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}