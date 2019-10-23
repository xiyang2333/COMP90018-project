package com.unimelb.studypartner.service.bo;

import com.unimelb.studypartner.dao.Activity;
import com.unimelb.studypartner.dao.Post;
import com.unimelb.studypartner.dao.Tag;

import java.util.List;

/**
 * Created by xiyang on 2019/10/22
 */
public class MainPageBO {

    private List<Activity> activityList;
    private List<Post> postList;
    private List<Integer> userInterestedTag;

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<Integer> getUserInterestedTag() {
        return userInterestedTag;
    }

    public void setUserInterestedTag(List<Integer> userInterestedTag) {
        this.userInterestedTag = userInterestedTag;
    }
}
