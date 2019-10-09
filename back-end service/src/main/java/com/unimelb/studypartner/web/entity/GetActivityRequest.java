package com.unimelb.studypartner.web.entity;

/**
 * Created by xiyang on 2019/10/9
 */
public class GetActivityRequest {
    private int activityId;
    private int userId;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
