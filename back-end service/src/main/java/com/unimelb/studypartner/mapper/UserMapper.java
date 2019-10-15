package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByUserName(String userName);

    User selectByEmail(String email);

    User seletctByGoogleId(String googleId);

    List<User> selectInList(int[] ids);
}