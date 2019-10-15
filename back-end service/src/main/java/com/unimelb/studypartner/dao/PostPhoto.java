package com.unimelb.studypartner.dao;

import java.io.Serializable;

public class PostPhoto implements Serializable {
    private Integer postPhotoId;

    private Integer postId;

    private String photo;

    private static final long serialVersionUID = 1L;

    public Integer getPostPhotoId() {
        return postPhotoId;
    }

    public void setPostPhotoId(Integer postPhotoId) {
        this.postPhotoId = postPhotoId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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
        sb.append(", postPhotoId=").append(postPhotoId);
        sb.append(", postId=").append(postId);
        sb.append(", photo=").append(photo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}