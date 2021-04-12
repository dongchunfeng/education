package com.online.system.controller;

import com.online.server.domain.Test;
import com.online.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/9 12:16
 */
@RestController
public class TestController {


    @Resource
    private TestService testService;

    @RequestMapping("/test")
    public List<Test> test(){
        return testService.findAll();
    }

}
