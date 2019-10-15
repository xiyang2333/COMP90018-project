package com.unimelb.studypartner.service;

import com.unimelb.studypartner.common.CommonException;
import com.unimelb.studypartner.dao.Activity;
import com.unimelb.studypartner.dao.Post;
import com.unimelb.studypartner.service.bo.ActivityBO;
import com.unimelb.studypartner.service.bo.PostBO;

import java.util.List;

/**
 * Created by xiyang on 2019/10/3
 */
public interface IMainPageService {
    public int joinActivity(int userId, int activityId, String type, int participantId) throws CommonException;

    public void answerQuestion(int userId, int postId, String answer, List<String> photoList) throws CommonException;

    public List<Activity> getUserActivity(int userId, int pageSize, int pageNumber) throws CommonException;

    public List<Activity> getUserJoinActivity(int userId, int pageSize, int pageNumber) throws CommonException;

    public List<Post> getUserPost(int userId, int pageSize, int pageNumber) throws CommonException;

    public int CreateActivity(ActivityBO activity, List<Integer> userList) throws CommonException;

    public ActivityBO getActivity(int activityId, int userId) throws CommonException;

    public int CreatePost(PostBO postBO, List<String> photoList) throws CommonException;

    public PostBO getPost(int postId, int userId) throws CommonException;
}
