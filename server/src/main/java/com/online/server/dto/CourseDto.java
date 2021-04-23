package com.online.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CourseDto {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 概述
     */
    private String summary;

    /**
     * 时长|单位秒
     */
    private Integer time;

    /**
     * 价格(元)
     */
    private BigDecimal price;

    /**
     * 封面
     */
    private String image;

    /**
     * 级别|ONE("1","初级"),TOW("2","中级"),THREE("3","高级")
     */
    private String level;

    /**
     * 收费c 收费 f 免费
     */
    private String charge;

    /**
     * 状态p 发布 d 草稿
     */
    private String status;

    /**
     * 报名数
     */
    private Integer enroll;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateAt;

    private List<CategoryDto> categorys;

    private String teacherId;



}