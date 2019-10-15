package com.unimelb.studypartner.service.impl;

import com.unimelb.studypartner.common.PictureService;
import com.unimelb.studypartner.dal.IActivityAndPostDAL;
import com.unimelb.studypartner.common.CommonException;
import com.unimelb.studypartner.dal.IUserSearchDAL;
import com.unimelb.studypartner.dal.impl.UserSearchDAL;
import com.unimelb.studypartner.dao.Activity;
import com.unimelb.studypartner.dao.Post;
import com.unimelb.studypartner.dao.PostPhoto;
import com.unimelb.studypartner.dao.User;
import com.unimelb.studypartner.service.IMainPageService;
import com.unimelb.studypartner.service.bo.ActivityBO;
import com.unimelb.studypartner.service.bo.AnswerBO;
import com.unimelb.studypartner.service.bo.PostBO;
import com.unimelb.studypartner.service.bo.UserBO;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiyang on 2019/10/3
 */
@Service
public class MainPageService implements IMainPageService {
    private static Logger logger = Logger.getLogger(MainPageService.class);

    @Autowired
    IActivityAndPostDAL activityAndPostDAL;

    @Autowired
    IUserSearchDAL userSearchDAL;

    @Autowired
    PictureService pictureService;

    @Override
    public int joinActivity(int userId, int activityId, String type, int participantId) throws CommonException {
        try {
            Activity activity = activityAndPostDAL.getActivity(activityId);
            int resId = participantId;
            if (activity == null) {
                throw new CommonException("no such activity", -1);
            }
            if ("T".equals(type)) {
                resId = activityAndPostDAL.joinActivity(userId, activityId);
            } else if ("F".equals(type)) {
                activityAndPostDAL.unJoinActivity(participantId);
            } else {
                throw new CommonException("wrong type", -1);
            }
            return resId;
        } catch (CommonException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }

    }

    public void answerQuestion(int userId, int postId, String answer, List<String> photoList) throws CommonException {
        try {
            Post post = activityAndPostDAL.getPost(postId);
            if (post == null) {
                throw new CommonException("no such post", -1);
            }
            List<String> photoAddress = new ArrayList<>();
            if (photoList != null) {
                for (String photo : photoList) {
                    photoAddress.add(pictureService.uploadPic(photo));
                }
            }

            activityAndPostDAL.answerPost(userId, postId, answer, photoAddress);
        } catch (CommonException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    public List<Activity> getUserActivity(int userId, int pageSize, int pageNumber) throws CommonException {
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<Activity> activities = activityAndPostDAL.getActivityByUser(userId, offset, pageSize);
//            // set photo
//            // to be discussed
//            for(Activity activity : activities){
//                activity.setActivityPic(pictureService.getPic(activity.getActivityPhoto()));
//            }
            return activities;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    public List<Activity> getUserJoinActivity(int userId, int pageSize, int pageNumber) throws CommonException {
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<Activity> activities = activityAndPostDAL.getJoinActivityByUser(userId, offset, pageSize);
            return activities;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    public List<Post> getUserPost(int userId, int pageSize, int pageNumber) throws CommonException {
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<Post> posts = activityAndPostDAL.getPostByUser(userId, offset, pageSize);
            return posts;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    public int CreateActivity(ActivityBO activityBO, List<Integer> userList) throws CommonException {
        try {
            Activity activity = new Activity();
            BeanUtils.copyProperties(activityBO, activity);
            if (activityBO.getTagId() == 0) {
                activity.setTagId(null);
            }
            activity.setActivityTime(activityBO.getTime());
            activity.setActivityCreateTime(new Date());

            if (activityBO.getPicture() != null && activityBO.getPicture().length() > 0) {
                String url = pictureService.uploadPic(activityBO.getPicture());
                activity.setActivityPhoto(url);
            }

            return activityAndPostDAL.createActivity(activity, userList);
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    public ActivityBO getActivity(int activityId, int userId) throws CommonException {
        try {
            ActivityBO activityBO = new ActivityBO();
            Activity activity = activityAndPostDAL.getActivity(activityId);
            if (activity.getTagId() == null) {
                activity.setTagId(0);
            }
            BeanUtils.copyProperties(activity, activityBO);

            // get create user
            activityBO.setCreateUser(getUserBO(activity.getUserId()));

            // set photo
            if (activity.getActivityPhoto() != null && activity.getActivityPhoto().length() > 0) {
                activityBO.setPicture(pictureService.getPic(activity.getActivityPhoto()));
            }
            // set userList
            List<User> userList = activityAndPostDAL.getUserListByActivity(activityId);
            if (userList != null && userList.size() > 0) {
                int participantId = 0;
                List<UserBO> userBOList = new ArrayList<>();
                for (User user : userList) {
                    UserBO userbo = new UserBO();
                    userbo.setUserId(user.getUserId());
                    userbo.setUserName(user.getUserLoginName());
                    if (user.getUserPhoto() != null && user.getUserPhoto().length() > 0) {
                        userbo.setUserPhoto(pictureService.getPic(user.getUserPhoto()));
                    }
                    if (user.getUserId() == userId) {
                        participantId = userId;
                    }
                    userBOList.add(userbo);
                }
                activityBO.setParticipantId(participantId);
                activityBO.setUserBOList(userBOList);
            }
            return activityBO;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    public int CreatePost(PostBO postBO, List<String> photoList) throws CommonException {
        try {
            Post post = new Post();
            BeanUtils.copyProperties(postBO, post);
            if (postBO.getTagId() == 0) {
                post.setTagId(null);
            }
            post.setPostDatetime(new Date());

            List<String> photoAddress = new ArrayList<>();
            if (photoList != null) {
                for (String photo : photoList) {
                    photoAddress.add(pictureService.uploadPic(photo));
                }
            }

            return activityAndPostDAL.createPost(post, photoAddress);
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    public PostBO getPost(int postId, int userId) throws CommonException {
        try {
            Post post = activityAndPostDAL.getPost(postId);
            if(post.getTagId() == null){
                post.setTagId(0);
            }
            PostBO postBO = new PostBO();
            BeanUtils.copyProperties(post, postBO);

            List<PostPhoto> photoList = activityAndPostDAL.getPostPhotoList(postId);
            if(photoList != null && photoList.size() > 0){
                List<String> picList = new ArrayList<>();
                for(PostPhoto photo : photoList){
                    picList.add(pictureService.getPic(photo.getPhoto()));
                }
                postBO.setPhotoList(picList);
            }

            // get create user
            postBO.setCreateUser(getUserBO(post.getUserId()));

            // get the answer
            List<AnswerBO> answerBOList = new ArrayList<>();
            postBO.setAnswerBOList(answerBOList);

            return postBO;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    private UserBO getUserBO(int userId) throws Exception{
        User user = userSearchDAL.getUser(userId);
        UserBO userBO = new UserBO();
        userBO.setUserName(user.getUserLoginName());
        userBO.setUserId(user.getUserId());
        if(user.getUserPhoto() != null && user.getUserPhoto().length() > 0){
            userBO.setUserPhoto(pictureService.getPic(user.getUserPhoto()));
        }

        return userBO;
    }
}
