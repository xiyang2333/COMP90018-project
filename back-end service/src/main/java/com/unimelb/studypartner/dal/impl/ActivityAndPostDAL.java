package com.unimelb.studypartner.dal.impl;

import com.unimelb.studypartner.dal.IActivityAndPostDAL;
import com.unimelb.studypartner.dao.*;
import com.unimelb.studypartner.mapper.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiyang on 2019/10/3
 */
@Repository
public class ActivityAndPostDAL implements IActivityAndPostDAL {
    private static Logger logger = Logger.getLogger(ActivityAndPostDAL.class);

    @Autowired
    ParticipantMapper participantMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    AnswerPhotoMapper answerPhotoMapper;

    @Override
    public int joinActivity(int userId, int activityId) throws SQLException {
        Participant participant = new Participant();
        participant.setMeetingId(activityId);
        participant.setUserId(userId);
        // participant status "J" -> join "S" -> sign in "W" -> wait to sign in N -> unjoin
        participant.setParticipantStatus("J");

        participantMapper.insert(participant);
        return participant.getParticipantId();
    }

    public int unJoinActivity(int participantId) throws SQLException {
        Participant participant = participantMapper.selectByPrimaryKey(participantId);
        participant.setParticipantStatus("N");
        participantMapper.updateByPrimaryKey(participant);
        return participantId;
    }

    @Override
    public Activity getActivity(int activityId) throws SQLException {
        return activityMapper.selectByPrimaryKey(activityId);
    }

    @Override
    public Post getPost(int postId) throws SQLException{
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    @Transactional
    public int answerPost(int userId, int postId, String answer, List<String> photoList) throws SQLException{
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setCommentDescription(answer);
        int id = commentMapper.insert(comment);
        for(String photo : photoList){
            AnswerPhoto answerPhoto = new AnswerPhoto();
            answerPhoto.setCommentId(id);
            answerPhoto.setPhoto(photo);
            answerPhotoMapper.insert(answerPhoto);
        }
        return id;
    }

    @Override
    public List<Activity> getActivityByUser(int userId, int offset, int pageSize) throws SQLException {
        return activityMapper.selctByUserId(userId, offset, pageSize);
    }

    @Override
    public List<Activity> getJoinActivityByUser(int userId, int offset, int pageSize) throws SQLException{
        List<Participant> participants = participantMapper.selectByUser(userId, offset, pageSize);
        List<Activity> activities = new ArrayList<>();
        if(participants != null && participants.size() > 0) {
            int[] activityIds = new int[participants.size()];
            for (int i = 0; i < participants.size(); i++) {
                activityIds[i] = participants.get(i).getMeetingId();
            }
            activities = activityMapper.selectJoinActivity(activityIds);
        }
        return activities;
    }

    @Override
    public List<Post> getPostByUser(int userId, int offset, int pageSize) throws SQLException {
        return postMapper.selctByUserId(userId, offset, pageSize);
    }
}
