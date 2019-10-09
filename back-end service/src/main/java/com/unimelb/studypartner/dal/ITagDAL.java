package com.unimelb.studypartner.dal;

import com.unimelb.studypartner.dao.Tag;
import com.unimelb.studypartner.dao.UserTag;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by xiyang on 2019/9/11
 */
public interface ITagDAL {
    public List<UserTag> getAllUserTag(int userId) throws SQLException;

    public void updateUserTag(UserTag userTag) throws SQLException;

    public List<Tag> getAllTag() throws SQLException;

    public void updateUserTags(Set<Integer> deleteTags, Set<Integer> addTags, int userId) throws SQLException;
}
