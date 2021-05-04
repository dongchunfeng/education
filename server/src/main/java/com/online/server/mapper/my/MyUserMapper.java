package com.online.server.mapper.my;

import com.online.server.dto.ResourceDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/4 17:55
 */
public interface MyUserMapper {

    List<ResourceDto> findResources(@Param("userId")String userId);


}
