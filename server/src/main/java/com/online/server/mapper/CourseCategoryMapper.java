package com.online.server.mapper;

import com.online.server.domain.CourseCategory;
import com.online.server.domain.CourseCategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseCategoryMapper {
    long countByExample(CourseCategoryExample example);

    int deleteByExample(CourseCategoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseCategory record);

    int insertSelective(CourseCategory record);

    List<CourseCategory> selectByExample(CourseCategoryExample example);

    CourseCategory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseCategory record, @Param("example") CourseCategoryExample example);

    int updateByExample(@Param("record") CourseCategory record, @Param("example") CourseCategoryExample example);

    int updateByPrimaryKeySelective(CourseCategory record);

    int updateByPrimaryKey(CourseCategory record);
}