package com.unimelb.studypartner.service.impl;

import com.alibaba.fastjson.JSON;
import com.unimelb.studypartner.common.PictureService;
import com.unimelb.studypartner.common.RedisUtil;
import com.unimelb.studypartner.dal.ITagDAL;
import com.unimelb.studypartner.dal.IUserSearchDAL;
import com.unimelb.studypartner.common.CommonException;
import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.dao.User;
import com.unimelb.studypartner.dao.UserTag;
import com.unimelb.studypartner.service.IUserService;
import com.unimelb.studypartner.service.bo.MeetingBO;
import com.unimelb.studypartner.service.bo.MeetingSearchBO;
import com.unimelb.studypartner.service.bo.RegisterBO;
import com.unimelb.studypartner.web.entity.UserPart;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by xiyang on 2019/9/10
 */
@Service
public class UserService implements IUserService {
    private static Logger logger = Logger.getLogger(UserService.class);
    private static final String RANK_TITLE = "USER_SEARCH_RANK";
    private static final String USER_PHOTO = "user_photo_";

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ITagDAL tagDAL;

    @Autowired
    IUserSearchDAL userSearchDAL;

    @Autowired
    PictureService pictureService;

    @Override
    public int loginCheck(String loginName, String password) throws CommonException {
        try {
            User user = userSearchDAL.getUserByNameOrEmail(loginName, loginName);

            if (user == null || !password.equals(user.getUserPassword())) {
                CommonException exception = new CommonException("user check failed: no user or wrong password", -1);
                throw exception;
            }

            return user.getUserId();
        } catch (CommonException ex) {
            throw ex;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            CommonException exception = new CommonException(e.getMessage(), -1);

            throw exception;
        }
    }

    // not use any more
    @Override
    public List<MeetingBO> searchMeeting(final MeetingSearchBO meetingSearchBO) throws CommonException {
        try {
            List<MeetingBO> meetingBOList = null;

            // update user search things
            if (meetingSearchBO.getTagId() != 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            rankSet(meetingSearchBO.getUserId(), meetingSearchBO.getTagId());
                        } catch (Exception ex) {
                            logger.error(ex.getMessage());
                        }
                    }
                }).start();
            }

            return meetingBOList;
        } catch (Exception ex) {
            CommonException exception = new CommonException(ex.getMessage(), -1);
            throw exception;
        }
    }

    @Override
    public List<Tag> getAllSearchTag(int userId) throws CommonException {
        List<Tag> tagList = new ArrayList<>();
        try {
            //get All tags
            List<Tag> originalTagList = tagDAL.getAllTag();
            if (originalTagList == null) {
                originalTagList = new ArrayList<>();
            }
            //get users tag rank
            List<UserTag> userTags = rankGet(userId);
            //use count to only find top 3
            int count = 3;
            if (userTags != null) {
                //add user tag first
                for (UserTag userTag : userTags) {
                    if (count == 0) {
                        break;
                    }
                    for (Tag tag : originalTagList) {
                        if (tag.getTagId().equals(userTag.getTagId())) {
                            tagList.add(tag);
                            count--;
                            break;
                        }
                    }
                }
            }
            //add all to taglist
            Tag all = new Tag();
            all.setTagId(0);
            all.setTagName("---ALL---");
            tagList.add(all);
            //add rest
            for (Tag tag : originalTagList) {
                if (!tagList.contains(tag)) {
                    tagList.add(tag);
                }
            }
        } catch (Exception ex) {
            CommonException exception = new CommonException(ex.getMessage(), -1);
            throw exception;
        }

        return tagList;
    }

    @Override
    public List<Tag> getAllTag() throws CommonException {
        try {
            return userSearchDAL.getAllTag();
        } catch (Exception ex) {
            CommonException exception = new CommonException(ex.getMessage(), -1);
            throw exception;
        }
    }

    public RegisterBO register(User user, String userPic) throws CommonException {
        RegisterBO registerBO = new RegisterBO();
        try {
            boolean newUser = true;
            // check user exists or not
            User pastUser = userSearchDAL.getUserByNameOrEmail(user.getUserLoginName(), user.getUserEmail());
            if (pastUser != null) {
                CommonException exception = new CommonException("user Name or email exists", -1);
                throw exception;
            }
            if (user.getGoogleId() != null && user.getGoogleId().length() > 0) {
                // search db
                User dbUser = userSearchDAL.getUserByGoogleId(user.getGoogleId());
                if (dbUser != null && dbUser.getUserId() > 0) {
                    registerBO.setUserId(dbUser.getUserId());
                    newUser = false;
                }
            }
            if (newUser) {
                // deal with pic
                if (userPic != null && userPic.length() > 0) {
                    String pathName = pictureService.uploadPic(userPic);
                    user.setUserPhoto(pathName);
                }
                // insert db
                int userId = userSearchDAL.insertUser(user);
                registerBO.setUserId(userId);
            }
            registerBO.setNewUser(newUser);
        } catch (CommonException ex) {
            throw ex;
        } catch (Exception ex) {
            CommonException exception = new CommonException(ex.getMessage(), -1);
            throw exception;
        }
        return registerBO;
    }

    public void updateTag(int userId, List<Integer> tags) throws CommonException {
        try {
            // get all user tags;
            List<UserTag> userTags = tagDAL.getAllUserTag(userId);
            //add sets use tag id
            Set<Integer> addSets = new HashSet<>(tags);
            // deleteSets use user tag id
            Set<Integer> deleteSets = new HashSet<>();
            if (userTags != null && userTags.size() > 0) {
                for (UserTag userTag : userTags) {
                    boolean contains = false;
                    for (int tag : tags) {
                        if (tag == userTag.getTagId()) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        deleteSets.add(userTag.getUserTagId());
                    }
                    addSets.remove(userTag.getTagId());
                }
            }
            tagDAL.updateUserTags(deleteSets, addSets, userId);

            // delete redis
            String redisKey = RANK_TITLE + userId;
            redisUtil.del(redisKey);
        } catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }

    @Override
    public void rankSet(int userId, int tagId) throws Exception {
        String redisKey = RANK_TITLE + userId;
        // get list from redis and transfer to object
        Object obj = redisUtil.get(redisKey);
        String rankListStr = obj == null ? "" : obj.toString();
        List<UserTag> userTags = new ArrayList<>();

        if (rankListStr != null && rankListStr.length() > 0) {
            userTags = JSON.parseArray(rankListStr, UserTag.class);
        }

        // if no data
        if (userTags == null || userTags.size() == 0) {
            userTags = tagDAL.getAllUserTag(userId);
        }
        // if still no data
        if (userTags == null) {
            userTags = new ArrayList<>();
        }
        boolean inList = false;
        // there are tags
        for (UserTag userTag : userTags) {
            if (userTag.getTagId() == tagId) {
                inList = true;
                userTag.setTimes(userTag.getTimes() + 1);
                userTag.setLastSearchTime(new Date());
                // if it's user favorite tag
                if (userTag.getUserTagId() != null && userTag.getUserTagId() > 0) {
                    tagDAL.updateUserTag(userTag);
                }
            }
        }
        // if not in list need to insert it
        if (!inList) {
            UserTag userTag = new UserTag();
            userTag.setUserId(userId);
            userTag.setTagId(tagId);
            userTag.setLastSearchTime(new Date());
            userTag.setTimes(1);
            userTags.add(userTag);
        }

        String value = JSON.toJSONString(userTags);
        if (!redisUtil.set(redisKey, value)) {
            logger.error(String.format("set redis failed : redis key: %s; value : %s", redisKey, value));
        }
    }

    @Override
    public List<UserTag> rankGet(int userId) throws Exception {
        String redisKey = RANK_TITLE + userId;
        // get list from redis and transfer to object
        Object obj = redisUtil.get(redisKey);
        String rankListStr = obj == null ? "" : obj.toString();
        List<UserTag> userTags = new ArrayList<>();

        if (rankListStr != null && rankListStr.length() > 0) {
            userTags = JSON.parseArray(rankListStr, UserTag.class);
        }

        // if no data
        if (userTags == null || userTags.size() == 0) {
            userTags = tagDAL.getAllUserTag(userId);
        }
        // if still no data
        if (userTags == null) {
            userTags = new ArrayList<>();
        }

        userTags.sort(new Comparator<UserTag>() {
            @Override
            public int compare(UserTag o1, UserTag o2) {
                double o1Date = ((double) (o1.getLastSearchTime().getTime() - new Date().getTime())) / 1000 / 600;
                BigDecimal o1DateReflect = new BigDecimal(o1Date);
                BigDecimal o1Value = new BigDecimal(o1.getTimes()).add(o1DateReflect);


                double o2Date = ((double) (o2.getLastSearchTime().getTime() - new Date().getTime())) / 1000 / 600;
                BigDecimal o2DateReflect = new BigDecimal(o2Date);
                BigDecimal o2Value = new BigDecimal(o2.getTimes()).add(o2DateReflect);

                return o2Value.compareTo(o1Value);
            }
        });

        return userTags;
    }

    public UserPart getUser(int userId) throws CommonException{
        try{
            User user = userSearchDAL.getUser(userId);
            UserPart userPart = new UserPart();
            userPart.setUserId(userId);
            userPart.setUserName(user.getUserLoginName());
            if(userPart.getUserPhoto() != null && userPart.getUserPhoto().length() > 0) {
                userPart.setUserPhoto(pictureService.getPic(userPart.getUserPhoto()));
            }

            return userPart;
        }catch (Exception ex) {
            throw new CommonException(ex.getMessage(), -1);
        }
    }
}