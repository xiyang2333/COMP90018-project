package com.unimelb.studypartner.dal.impl;

import com.unimelb.studypartner.dal.IUserSearchDAL;
import com.unimelb.studypartner.dao.User;
import com.unimelb.studypartner.mapper.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * Created by xiyang on 2019/9/12
 */
@Repository
public class UserSearchDAL implements IUserSearchDAL {
    private static Logger logger = Logger.getLogger(UserSearchDAL.class);

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByNameOrEmail(String loginName) throws SQLException{
        User user = userMapper.selectByUserName(loginName);
        if(user == null){
            user = userMapper.selectByEmail(loginName);
        }

        return user;
    }
}
