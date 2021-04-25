package com.online.system.controller.admin;

import com.online.server.dto.*;
import com.online.server.service.UserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/14 10:51
 */
@RestController
@RequestMapping("/admin")
public class UserController {

    @Resource
    private UserService userService;
    public static final String BUSINESS_NAME = "用户";

    /**
     * 分页查询
     * @param page
     * @param size
     * @param qs
     * @return
     */
    @RequestMapping(path = "/user/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size, QueryUserDto qs) {
        PageDto all = userService.findAll(page, size, qs.getName());
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
    public ResponseDto login(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        LoginUserDto login = userService.login(userDto);
        return  new ResponseDto().ok(0,"登录成功!",login);
    }


}
