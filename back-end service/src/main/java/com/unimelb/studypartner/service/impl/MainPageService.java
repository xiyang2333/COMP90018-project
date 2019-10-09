package com.unimelb.studypartner.service.impl;

import com.unimelb.studypartner.common.PictureService;
import com.unimelb.studypartner.dal.IActivityAndPostDAL;
import com.unimelb.studypartner.common.CommonException;
import com.unimelb.studypartner.dao.Activity;
import com.unimelb.studypartner.dao.Post;
import com.unimelb.studypartner.service.IMainPageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    PictureService pictureService;

    @Override
    public int joinActivity(int userId, int activityId, String type, int participantId) throws CommonException {
        try {
            Activity activity = activityAndPostDAL.getActivity(activityId);
            int resId= participantId;
            if (activity == null) {
                throw new CommonException("no such activity", -1);
            }
            if("T".equals(type)) {
                resId = activityAndPostDAL.joinActivity(userId, activityId);
            } else if ("F".equals(type)){
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

    public void answerQuestion(int userId, int postId, String answer, List<String> photoList) throws CommonException{
        try {
            Post post = activityAndPostDAL.getPost(postId);
            if(post == null){
                throw new CommonException("no such post", -1);
            }
            List<String> photoAddress = new ArrayList<>();
            if(photoList != null) {
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

    public List<Activity> getUserActivity(int userId, int pageSize, int pageNumber) throws CommonException{
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

    public List<Activity> getUserJoinActivity(int userId, int pageSize, int pageNumber) throws CommonException{
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<Activity> activities = activityAndPostDAL.getJoinActivityByUser(userId, offset, pageSize);
            return activities;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    public List<Post> getUserPost(int userId, int pageSize, int pageNumber) throws CommonException{
        try {
            int offset = (pageNumber - 1) * pageSize;
            List<Post> posts = activityAndPostDAL.getPostByUser(userId, offset, pageSize);
            return posts;
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }
}
