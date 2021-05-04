package com.online.system.controller.admin;

import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.dto.RoleUserDto;
import com.online.server.service.RoleUserService;
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
public class RoleUserController {

    @Resource
    private RoleUserService roleUserService;
    public static final String BUSINESS_NAME = "用户角色关联";

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(path = "/roleUser/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size) {
        PageDto all = roleUserService.findAll(page, size);
        return new ResponseDto().ok(0, "用户角色关联列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param roleUserDto
     * @return
     */
    @RequestMapping(path = "/roleUser/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody RoleUserDto roleUserDto) {
        return roleUserService.save(roleUserDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/roleUser/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = roleUserService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "用户角色关联删除成功");
        }
        return ResponseDto.fail(1, "用户角色关联删除失败");
    }

}
