package com.unimelb.studypartner.web.entity;

import java.util.List;

/**
 * Created by xiyang on 2019/10/3
 */
public class UserActivityResponse {
    private int responseStatus;
    private String errorMessage;

    private List<ActivityPart> activityPartList;

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

    public List<ActivityPart> getActivityPartList() {
        return activityPartList;
    }

    public void setActivityPartList(List<ActivityPart> activityPartList) {
        this.activityPartList = activityPartList;
    }
}
