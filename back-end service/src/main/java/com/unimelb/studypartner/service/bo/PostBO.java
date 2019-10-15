package com.unimelb.studypartner.service.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by xiyang on 2019/10/10
 */
public class PostBO {
    private Integer postId;
    private String postName;
    private String postDescription;
    private Date postDatetime;
    private Integer userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer tagId;
    private UserBO createUser;
    private List<AnswerBO> answerBOList;
    private List<String> photoList;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public UserBO getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserBO createUser) {
        this.createUser = createUser;
    }

    public List<AnswerBO> getAnswerBOList() {
        return answerBOList;
    }

    public void setAnswerBOList(List<AnswerBO> answerBOList) {
        this.answerBOList = answerBOList;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }
}
