package com.online.server.mapper.my;

import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/20 18:07
 */
public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);

}
