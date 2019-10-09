package com.unimelb.studypartner.dal;

import com.unimelb.studypartner.dao.Activity;
import com.unimelb.studypartner.dao.Post;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by xiyang on 2019/10/3
 */
public interface IActivityAndPostDAL {
    public Activity getActivity(int activityId) throws SQLException;

    public Post getPost(int postId) throws SQLException;

    public int joinActivity(int userId, int activityId) throws SQLException;

    public int unJoinActivity(int participantId) throws SQLException;

    public int answerPost(int userId, int postId, String answer, List<String> photoList) throws SQLException;

    public List<Activity> getActivityByUser(int userId, int offset, int pageSize) throws SQLException;

    public List<Activity> getJoinActivityByUser(int userId, int offset, int pageSize) throws SQLException;

    public List<Post> getPostByUser(int userId, int offset, int pageSize) throws SQLException;
}
