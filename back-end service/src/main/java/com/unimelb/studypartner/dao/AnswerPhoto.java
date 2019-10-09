package com.unimelb.studypartner.dao;

import java.io.Serializable;

public class AnswerPhoto implements Serializable {
    private Integer answerPhotoId;

    private Integer commentId;

    private String photo;

    private static final long serialVersionUID = 1L;

    public Integer getAnswerPhotoId() {
        return answerPhotoId;
    }

    public void setAnswerPhotoId(Integer answerPhotoId) {
        this.answerPhotoId = answerPhotoId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", answerPhotoId=").append(answerPhotoId);
        sb.append(", commentId=").append(commentId);
        sb.append(", photo=").append(photo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}