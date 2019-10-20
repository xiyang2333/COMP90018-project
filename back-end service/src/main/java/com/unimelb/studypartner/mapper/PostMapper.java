package com.unimelb.studypartner.mapper;

import com.unimelb.studypartner.dao.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PostMapper {
    int deleteByPrimaryKey(Integer postId);

    int insert(Post record);

    Post selectByPrimaryKey(Integer postId);

    List<Post> selectAll();

    int updateByPrimaryKey(Post record);

    List<Post> selctByUserId(@Param("userId") Integer userId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<Post> searchPost(@Param("tagId") Integer tagId, @Param("offset") int offset, @Param("pageSize") int pageSize,
                                  @Param("longitudeRight") BigDecimal longitudeRight, @Param("longitudeLeft")BigDecimal longitudeLeft,
                                  @Param("latitudeUp")BigDecimal latitudeUp, @Param("latitudeDown")BigDecimal latitudeDown);
}