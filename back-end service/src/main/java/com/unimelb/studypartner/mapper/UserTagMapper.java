package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.UserTag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagMapper {
    int deleteByPrimaryKey(Integer userTagId);

    int insert(UserTag record);

    UserTag selectByPrimaryKey(Integer userTagId);

    List<UserTag> selectAll();

    int updateByPrimaryKey(UserTag record);

    int setUnValidByPrimaryKey(Integer userTagId);

    List<UserTag> selectAllByUser(Integer userId);
}