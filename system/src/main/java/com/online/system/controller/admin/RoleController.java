package com.online.system.controller.admin;

import com.online.server.dto.*;
import com.online.server.service.RoleResourceService;
import com.online.server.service.RoleService;
import com.online.server.service.RoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
public class RoleController {

    @Resource
    private RoleService roleService;
    public static final String BUSINESS_NAME = "角色";
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private RoleUserService roleUserService;

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

    @PostMapping("/role/saveResource")
    public ResponseDto<RoleDto> saveResource(@RequestBody RoleDto roleDto){
        log.info("保存角色资源关联开始");
        roleService.saveResource(roleDto);
        return new ResponseDto().ok(0,"添加资源成功",roleDto);
    }

    @PostMapping("/role/saveUser")
    public ResponseDto<RoleDto> saveUser(@RequestBody RoleDto roleDto){
        log.info("保存用户角色关联开始");
        roleService.saveUser(roleDto);
        return new ResponseDto().ok(0,"添加用户成功",roleDto);
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

    @RequestMapping(path = "/role/listRoleResource/{roleId}", method = RequestMethod.GET)
    public ResponseDto listRoleResource(@PathVariable String roleId) {
        List<RoleResourceDto> allRoleResourceByRoleId = roleResourceService.findAllRoleResourceByRoleId(roleId);
        if (allRoleResourceByRoleId.size() > 0) {
            return new ResponseDto().ok(0, "查询角色资源成功",allRoleResourceByRoleId);
        }
        return ResponseDto.fail(0, "查询角色资源失败");
    }

    @RequestMapping(path = "/role/listRoleUser/{roleId}", method = RequestMethod.GET)
    public ResponseDto listRoleUser(@PathVariable String roleId) {
        List<RoleUserDto> roleUserByRoleId = roleUserService.findRoleUserByRoleId(roleId);
        if (roleUserByRoleId.size() > 0) {
            return new ResponseDto().ok(0, "查询用户角色成功",roleUserByRoleId);
        }
        return ResponseDto.fail(0, "查询用户角色失败");
    }

}
