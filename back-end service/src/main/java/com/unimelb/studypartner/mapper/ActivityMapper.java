package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ActivityMapper {
    int deleteByPrimaryKey(Integer activityId);

    int insert(Activity record);

    Activity selectByPrimaryKey(Integer activityId);

    List<Activity> selectAll();

    int updateByPrimaryKey(Activity record);

    List<Activity> selctByUserId(@Param("userId") Integer userId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<Activity> selectJoinActivity(int[] ids);

    List<Activity> searchActivity(@Param("tagId") Integer tagId, @Param("offset") int offset, @Param("pageSize") int pageSize,
                                  @Param("longitudeRight")BigDecimal longitudeRight, @Param("longitudeLeft")BigDecimal longitudeLeft,
                                  @Param("latitudeUp")BigDecimal latitudeUp, @Param("latitudeDown")BigDecimal latitudeDown);
}