package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.Participant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantMapper {
    int deleteByPrimaryKey(Integer participantId);

    int insert(Participant record);

    Participant selectByPrimaryKey(Integer participantId);

    List<Participant> selectAll();

    int updateByPrimaryKey(Participant record);

    List<Participant> selectByUser(@Param("userId") int userId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<Participant> selectByActivity(int activityId);
}