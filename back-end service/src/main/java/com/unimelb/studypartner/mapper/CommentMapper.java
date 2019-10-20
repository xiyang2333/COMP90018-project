package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByPostId(Integer postId);
}