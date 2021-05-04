package com.online.server.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
public class LoginUserDto {

    /**
     * id
     */
    private String id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String name;

    private String token;

    /**
     * 当前用户的资源
     * 用于前端界面控制
     */
    private List<ResourceDto> resources;

    /**
     * 当前用户的拥有的请求
     * 所有资源中的请求，用于后端接口拦截
     */
    private HashSet<String> requests;

}