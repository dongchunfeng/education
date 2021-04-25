package com.online.system.controller.admin;

import com.google.code.kaptcha.Producer;
import com.online.server.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/25 17:33
 */
@RestController
@RequestMapping("/admin/kaptcha")
public class KaptchaController {

    @Autowired
    Producer kaptchaProducer;
    @Autowired
    private RedisTemplate redisTemplate;

    public static final String BUSINESS_NAME = "图形验证码";


    @GetMapping("/image-code/{imageCodeToken}")
    public void imageCode(@PathVariable String imageCodeToken, HttpServletRequest request,
                          HttpServletResponse response) throws Exception {

        ByteArrayOutputStream jpegOutput = new ByteArrayOutputStream();
        try{
            String text = kaptchaProducer.createText();

            Cookie cookie = new Cookie("educationKaptcha", imageCodeToken);
            //request.getSession().setAttribute(imageCodeToken,text);
            cookie.setMaxAge(60);
            cookie.setPath("/");
            response.addCookie(cookie);
            String kaptcha = RedisKeyUtil.getKaptcha(imageCodeToken);
            redisTemplate.opsForValue().set(kaptcha,text,60, TimeUnit.SECONDS);

            BufferedImage image = kaptchaProducer.createImage(text);
            ImageIO.write(image,"jpg",jpegOutput);

        }catch (IllegalArgumentException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] bytes = jpegOutput.toByteArray();
        response.setHeader("Cahche-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();


    }


}
