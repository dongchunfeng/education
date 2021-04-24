package com.online.system.controller.admin;

import com.online.server.dto.PageDto;
import com.online.server.dto.QueryUserDto;
import com.online.server.dto.ResponseDto;
import com.online.server.dto.UserDto;
import com.online.server.service.UserService;
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

}
