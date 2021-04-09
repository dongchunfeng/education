package com.online.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/9 12:16
 */
@RestController
public class TestController {


    @RequestMapping("/test")
    public String test(){
        return "success";
    }

}
