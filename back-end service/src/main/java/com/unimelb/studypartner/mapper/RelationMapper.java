package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.Relation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationMapper {
    int deleteByPrimaryKey(Integer relationId);

    int insert(Relation record);

    Relation selectByPrimaryKey(Integer relationId);

    List<Relation> selectAll();

    int updateByPrimaryKey(Relation record);
}