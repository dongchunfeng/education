package com.online.server.service;

import com.online.server.domain.Test;
import com.online.server.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/9 14:24
 */
@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> findAll() {
        return testMapper.selectByExample(null);
    }


}
