package com.unimelb.studypartner.web.entity;

import com.unimelb.studypartner.dao.Tag;

import java.util.List;

/**
 * Created by xiyang on 2019/10/22
 */
public class MainPageResponse {
    private int responseStatus;
    private String errorMessage;

    private List<ActivityPart> activityList;
    private List<PostPart> postList;
    private List<Integer> userInterestedTag;

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

    public List<ActivityPart> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityPart> activityList) {
        this.activityList = activityList;
    }

    public List<PostPart> getPostList() {
        return postList;
    }

    public void setPostList(List<PostPart> postList) {
        this.postList = postList;
    }

    public List<Integer> getUserInterestedTag() {
        return userInterestedTag;
    }

    public void setUserInterestedTag(List<Integer> userInterestedTag) {
        this.userInterestedTag = userInterestedTag;
    }
}
