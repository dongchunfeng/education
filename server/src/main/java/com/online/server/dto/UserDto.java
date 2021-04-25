package com.online.server.dto;

import lombok.Data;

@Data
public class UserDto {

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

    /**
     * 密码
     */
    private String password;

    private String code;

    private String imageCodeToken;



}