package com.unimelb.studypartner.dal.impl;

import com.unimelb.studypartner.common.GeoEntity;
import com.unimelb.studypartner.dal.IActivityAndPostDAL;
import com.unimelb.studypartner.dao.*;
import com.unimelb.studypartner.mapper.*;
import com.unimelb.studypartner.service.bo.ActivityBO;
import com.unimelb.studypartner.service.bo.AnswerBO;
import com.unimelb.studypartner.service.bo.SearchEntity;
import com.unimelb.studypartner.service.bo.UserBO;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    UserMapper userMapper;

    @Autowired
    PostPhotoMapper postPhotoMapper;

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
    public Post getPost(int postId) throws SQLException {
        return postMapper.selectByPrimaryKey(postId);
    }

    @Override
    @Transactional
    public int answerPost(int userId, int postId, String answer, List<String> photoList) throws SQLException {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setCommentDescription(answer);
        commentMapper.insert(comment);
        for (String photo : photoList) {
            AnswerPhoto answerPhoto = new AnswerPhoto();
            answerPhoto.setCommentId(comment.getCommentId());
            answerPhoto.setPhoto(photo);
            answerPhotoMapper.insert(answerPhoto);
        }
        return comment.getCommentId();
    }

    @Override
    public List<Activity> getActivityByUser(int userId, int offset, int pageSize) throws SQLException {
        return activityMapper.selctByUserId(userId, offset, pageSize);
    }

    @Override
    public List<Activity> getJoinActivityByUser(int userId, int offset, int pageSize) throws SQLException {
        List<Participant> participants = participantMapper.selectByUser(userId, offset, pageSize);
        List<Activity> activities = new ArrayList<>();
        if (participants != null && participants.size() > 0) {
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

    @Override
    @Transactional
    public int createActivity(Activity activity, List<Integer> userList) throws SQLException {
        activityMapper.insert(activity);
        int activityId = activity.getActivityId();
        if (userList != null && userList.size() > 0) {
            for (Integer userId : userList) {

                Participant participant = new Participant();
                participant.setMeetingId(activityId);
                participant.setUserId(userId);
                // participant status "J" -> join "S" -> sign in "W" -> wait to sign in N -> unjoin
                participant.setParticipantStatus("J");

                participantMapper.insert(participant);
            }
        }
        return activityId;
    }

    @Override
    @Transactional
    public List<User> getUserListByActivity(int activityId) throws SQLException {
        List<Participant> participants = participantMapper.selectByActivity(activityId);
        if (participants != null && participants.size() > 0) {
            int[] ids = new int[participants.size()];

            for (int i = 0; i < participants.size(); i++) {
                ids[i] = participants.get(i).getUserId();
            }
            // get user List
            return userMapper.selectInList(ids);
        }
        return null;
    }

    @Override
    @Transactional
    public int createPost(Post post, List<String> photoList) throws SQLException {
        postMapper.insert(post);
        int postId = post.getPostId();

        if (photoList != null && photoList.size() > 0) {
            for (String photo : photoList) {
                PostPhoto postPhoto = new PostPhoto();
                postPhoto.setPostId(postId);
                postPhoto.setPhoto(photo);

                postPhotoMapper.insert(postPhoto);
            }
        }
        return postId;
    }

    @Override
    public List<PostPhoto> getPostPhotoList(int postId) throws SQLException {
        return postPhotoMapper.selectByPostId(postId);
    }

    @Override
    @Transactional
    public List<AnswerBO> getAnswers(int postId) throws SQLException {
        List<Comment> comments = commentMapper.selectByPostId(postId);
        List<AnswerBO> answerBOList = new ArrayList<>();

        for (Comment comment : comments) {
            AnswerBO answerBO = new AnswerBO();
            answerBO.setAnswer(comment.getCommentDescription());
            answerBO.setCommentId(comment.getCommentId());
            answerBO.setPostId(postId);

            User user = userMapper.selectByPrimaryKey(comment.getUserId());
            UserBO userBO = new UserBO();
            userBO.setUserId(user.getUserId());
            userBO.setUserName(user.getUserLoginName());
            userBO.setUserPhoto(user.getUserPhoto());
            answerBO.setUser(userBO);

            List<AnswerPhoto> answerPhotos = answerPhotoMapper.selectByCommentId(comment.getCommentId());
            answerBO.setPicList(answerPhotos);

            answerBOList.add(answerBO);
        }

        return answerBOList;
    }

    @Override
    public List<Activity> getSearchActivity(SearchEntity searchEntity, GeoEntity geoEntity, int offset, int pageSize) throws SQLException {
        BigDecimal longitudeRight = null;
        BigDecimal longitudeLeft = null;
        BigDecimal latitudeUp = null;
        BigDecimal latitudeDown = null;
        if (geoEntity != null) {
            longitudeRight = geoEntity.getLongitudeRight();
            longitudeLeft = geoEntity.getLongitudeLeft();
            latitudeUp = geoEntity.getLatitudeUp();
            latitudeDown = geoEntity.getLatitudeDown();
        }
        Integer tagId = null;
        if(searchEntity.getTagId() != 0){
            tagId = searchEntity.getTagId();
        }
        return activityMapper.searchActivity(tagId, offset, pageSize, longitudeRight, longitudeLeft, latitudeUp, latitudeDown);
    }

    @Override
    public List<Post> getSearchPost(SearchEntity searchEntity, GeoEntity geoEntity, int offset, int pageSize) throws SQLException{
        BigDecimal longitudeRight = null;
        BigDecimal longitudeLeft = null;
        BigDecimal latitudeUp = null;
        BigDecimal latitudeDown = null;
        if (geoEntity != null) {
            longitudeRight = geoEntity.getLongitudeRight();
            longitudeLeft = geoEntity.getLongitudeLeft();
            latitudeUp = geoEntity.getLatitudeUp();
            latitudeDown = geoEntity.getLatitudeDown();
        }
        Integer tagId = null;
        if(searchEntity.getTagId() != 0){
            tagId = searchEntity.getTagId();
        }
        return postMapper.searchPost(tagId, offset, pageSize, longitudeRight, longitudeLeft, latitudeUp, latitudeDown);
    }

}
