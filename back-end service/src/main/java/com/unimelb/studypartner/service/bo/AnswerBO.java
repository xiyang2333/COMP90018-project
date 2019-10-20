package com.unimelb.studypartner.service.bo;

import com.unimelb.studypartner.dao.AnswerPhoto;

import java.util.List;

/**
 * Created by xiyang on 2019/10/10
 */
public class AnswerBO {
    private Integer commentId;
    private Integer postId;
    private UserBO user;
    private String answer;
    private List<String> photoList;
    private List<AnswerPhoto> picList;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public UserBO getUser() {
        return user;
    }

    public void setUser(UserBO user) {
        this.user = user;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    public List<AnswerPhoto> getPicList() {
        return picList;
    }

    public void setPicList(List<AnswerPhoto> picList) {
        this.picList = picList;
    }
}
