package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.AnswerPhoto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerPhotoMapper {
    int deleteByPrimaryKey(Integer answerPhotoId);

    int insert(AnswerPhoto record);

    AnswerPhoto selectByPrimaryKey(Integer answerPhotoId);

    List<AnswerPhoto> selectAll();

    int updateByPrimaryKey(AnswerPhoto record);
}