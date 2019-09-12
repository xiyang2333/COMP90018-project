package com.unimelb.studypartner.dal;

import com.unimelb.studypartner.dao.User;

import java.sql.SQLException;

/**
 * Created by xiyang on 2019/9/12
 */
public interface IUserSearchDAL {
    public User getUserByNameOrEmail(String loginName) throws SQLException;
}
