package com.online.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.online.server.dto.*;
import com.online.server.service.UserService;
import com.online.server.util.CookieUtils;
import com.online.server.util.RedisKeyUtil;
import com.online.server.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/14 10:51
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    public static final String BUSINESS_NAME = "用户";

    /**
     * 分页查询
     * @param qs
     * @return
     */
    @RequestMapping(path = "/user/list", method = RequestMethod.POST)
    public ResponseDto list(@RequestBody  QueryUserDto qs) {
        PageDto all = userService.findAll(qs.getPage(), qs.getSize(), qs.getName());
        return new ResponseDto().ok(0, "用户列表查询成功", all);
    }

    @RequestMapping(path = "/user/list", method = RequestMethod.GET)
    public ResponseDto list() {
        List<UserDto> all = userService.findAll();
        return new ResponseDto().ok(0, "用户列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param userDto
     * @return
     */
    @RequestMapping(path = "/user/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        return userService.save(userDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/user/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = userService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "用户删除成功");
        }
        return ResponseDto.fail(1, "用户删除失败");
    }

    /**
     * 重置密码
     * @param userDto
     * @return
     */
    @RequestMapping(path = "/user/save-password", method = RequestMethod.POST)
    public ResponseDto savePassword(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        int i = userService.savePassword(userDto);
        if(i>0){
            return  new ResponseDto().ok(0,"保存密码成功!",userDto);
        }
        return  ResponseDto.fail(1,"保存密码失败!");
    }

    @RequestMapping(path = "/user/login", method = RequestMethod.POST)
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request) {
        log.info("用户登录开始");
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        //校验验证码
        //String imageCode = (String) request.getSession().getAttribute(userDto.getImageCodeToken());
        String educationKaptcha = CookieUtils.getValue(request, "educationKaptcha");
        if(StringUtils.isBlank(educationKaptcha)){
            log.info("用户登录失败 验证码已过期");
            return ResponseDto.fail(0,"验证码已过期");
        }else{
            String kaptchaKey = RedisKeyUtil.getKaptcha(educationKaptcha);
            String kaptcha =  (String)redisTemplate.opsForValue().get(kaptchaKey);
            if(!kaptcha.toLowerCase().equals(userDto.getCode().toLowerCase())){
                log.info("用户登录失败 验证码不对");
                return ResponseDto.fail(0,"验证码不对");
            }
        }

        LoginUserDto login = userService.login(userDto);
        String token = UuidUtil.getShortUuid();
        login.setToken(token);
       // request.getSession().setAttribute(Constants.LOGIN_USER,login);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(login),3600*24, TimeUnit.SECONDS);

        return  new ResponseDto().ok(0,"登录成功!",login);
    }

    @RequestMapping(path = "/user/logout/{token}", method = RequestMethod.GET)
    public ResponseDto logout(@PathVariable String token) {
        //request.getSession().removeAttribute(Constants.LOGIN_USER);
        redisTemplate.delete(token);
        log.info("从redis中删除token: {}",token);
        return ResponseDto.ok(0,"注销成功!");
    }

}
