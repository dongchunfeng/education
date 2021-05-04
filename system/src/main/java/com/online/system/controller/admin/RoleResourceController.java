package com.online.system.controller.admin;

import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.dto.RoleResourceDto;
import com.online.server.service.RoleResourceService;
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
public class RoleResourceController {

    @Resource
    private RoleResourceService roleResourceService;
    public static final String BUSINESS_NAME = "角色资源关联";

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(path = "/roleResource/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size) {
        PageDto all = roleResourceService.findAll(page, size);
        return new ResponseDto().ok(0, "角色资源关联列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param roleResourceDto
     * @return
     */
    @RequestMapping(path = "/roleResource/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody RoleResourceDto roleResourceDto) {
        return roleResourceService.save(roleResourceDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/roleResource/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = roleResourceService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "角色资源关联删除成功");
        }
        return ResponseDto.fail(1, "角色资源关联删除失败");
    }

}
