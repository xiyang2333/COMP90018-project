package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.Meeting;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingMapper {
    int deleteByPrimaryKey(Integer meetingId);

    int insert(Meeting record);

    Meeting selectByPrimaryKey(Integer meetingId);

    List<Meeting> selectAll();

    int updateByPrimaryKey(Meeting record);
}