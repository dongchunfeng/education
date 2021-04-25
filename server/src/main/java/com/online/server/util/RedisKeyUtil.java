package com.online.server.util;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/10/23
 */
public class RedisKeyUtil {

    public static final String SPLIT = ":";
    public static final String PREFIX_KAPTCHA = "kaptcha";
    public static final String PREFIX_TICKET = "ticket";
    public static final String PREFIX_USER = "user";

    /**
     * 登录验证码
     *
     * @param owner
     * @return
     */
    public static String getKaptcha(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    /**
     * ticket
     *
     * @param ticket
     * @return
     */
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }



}
