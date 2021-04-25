package com.online.server.dto;

import lombok.Data;

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


}