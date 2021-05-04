package com.online.system.controller.admin;

import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.dto.RoleDto;
import com.online.server.service.RoleService;
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
public class RoleController {

    @Resource
    private RoleService roleService;
    public static final String BUSINESS_NAME = "角色";

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(path = "/role/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size) {
        PageDto all = roleService.findAll(page, size);
        return new ResponseDto().ok(0, "角色列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param roleDto
     * @return
     */
    @RequestMapping(path = "/role/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/role/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = roleService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "角色删除成功");
        }
        return ResponseDto.fail(1, "角色删除失败");
    }

}
