package com.unimelb.studypartner.dal;

import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.dao.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by xiyang on 2019/9/12
 */
public interface IUserSearchDAL {
    public User getUserByNameOrEmail(String loginName, String loginEmail) throws SQLException;

    public List<Tag> getAllTag() throws SQLException;

    public User getUserByGoogleId(String googleId) throws SQLException;

    public int insertUser(User user) throws SQLException;

    public User getUser(int userId) throws SQLException;

}
