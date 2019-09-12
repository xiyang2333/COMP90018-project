package com.unimelb.studypartner.dal.impl;

import com.unimelb.studypartner.dal.ITagDAL;
import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.dao.User;
import com.unimelb.studypartner.dao.UserTag;
import com.unimelb.studypartner.mapper.TagMapper;
import com.unimelb.studypartner.mapper.UserTagMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiyang on 2019/9/11
 */
@Repository
public class TagDAL implements ITagDAL {
    private static Logger logger = Logger.getLogger(TagDAL.class);

    @Autowired
    UserTagMapper userTagMapper;

    @Autowired
    TagMapper tagMapper;

//    @Override
//    @Transactional
//    public List<UserTag> searchUpdate(int userId, int tagId) throws SQLException {
//        // get all user tags
//        List<UserTag> userTags = userTagMapper.selectAllByUser(userId);
//        if(userTags != null && userTags.size() > 0){
//            for (UserTag userTag : userTags) {
//                if(userTag.getTagId() == tagId) {
//                    // update userTag set times + 1
//                    userTag.setTimes(userTag.getTimes() + 1);
//                    userTag.setLastSearchTime(new Date());
//                    userTagMapper.updateByPrimaryKey(userTag);
//                    break;
//                }
//            }
//        }
//
//        return userTags;
//    }

    @Override
    public List<UserTag> getAllUserTag(int userId) throws SQLException{
        return userTagMapper.selectAllByUser(userId);
    }

    @Override
    public void updateUserTag(UserTag userTag) throws SQLException{
        userTagMapper.updateByPrimaryKey(userTag);
    }

    @Override
    public List<Tag> getAllTag() throws SQLException{
        return tagMapper.selectAll();
    }

}
