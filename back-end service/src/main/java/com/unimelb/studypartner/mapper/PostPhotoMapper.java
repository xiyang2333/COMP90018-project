package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.PostPhoto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostPhotoMapper {
    int deleteByPrimaryKey(Integer postPhotoId);

    int insert(PostPhoto record);

    PostPhoto selectByPrimaryKey(Integer postPhotoId);

    List<PostPhoto> selectAll();

    int updateByPrimaryKey(PostPhoto record);

    List<PostPhoto> selectByPostId(Integer postId);
}