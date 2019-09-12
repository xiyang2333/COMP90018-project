package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.Photo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoMapper {
    int deleteByPrimaryKey(Integer photoId);

    int insert(Photo record);

    Photo selectByPrimaryKey(Integer photoId);

    List<Photo> selectAll();

    int updateByPrimaryKey(Photo record);
}