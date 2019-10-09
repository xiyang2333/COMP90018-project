package com.unimelb.studypartner.dal.impl;

import com.unimelb.studypartner.dal.IUserSearchDAL;
import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.dao.User;
import com.unimelb.studypartner.mapper.TagMapper;
import com.unimelb.studypartner.mapper.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by xiyang on 2019/9/12
 */
@Repository
public class UserSearchDAL implements IUserSearchDAL {
    private static Logger logger = Logger.getLogger(UserSearchDAL.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    TagMapper tagMapper;

    @Override
    public User getUserByNameOrEmail(String loginName, String loginEmail) throws SQLException{
        User user = userMapper.selectByUserName(loginName);
        if(user == null){
            user = userMapper.selectByEmail(loginEmail);
        }

        return user;
    }

    @Override
    public List<Tag> getAllTag() throws SQLException {
        return tagMapper.selectAll();
    }

    @Override
    public User getUserByGoogleId(String googleId) throws SQLException{
        return userMapper.seletctByGoogleId(googleId);
    }

    @Override
    public int insertUser(User user) throws SQLException{
        userMapper.insert(user);
        return user.getUserId();
    }

}
