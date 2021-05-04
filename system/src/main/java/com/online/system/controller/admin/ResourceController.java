package com.online.system.controller.admin;

import com.online.server.dto.PageDto;
import com.online.server.dto.QueryResourceDto;
import com.online.server.dto.ResourceDto;
import com.online.server.dto.ResponseDto;
import com.online.server.service.ResourceService;
import org.springframework.web.bind.annotation.*;

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
public class ResourceController {

    @javax.annotation.Resource
    private ResourceService resourceService;
    public static final String BUSINESS_NAME = "资源";

    /**
     * 分页查询
     * @param page
     * @param size
     * @param qs
     * @return
     */
    @RequestMapping(path = "/resource/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size, QueryResourceDto qs) {
        PageDto all = resourceService.findAll(page, size, qs.getName());
        return new ResponseDto().ok(0, "资源列表查询成功", all);
    }

    @RequestMapping(path = "/resource", method = RequestMethod.GET)
    public List<ResourceDto> list() {
        return resourceService.findAll();
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param jsonStr
     * @return
     */
    @RequestMapping(path = "/resource/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody String jsonStr) {
        return resourceService.saveJson(jsonStr)==1?ResponseDto.ok(0,"保存资源成功"):ResponseDto.fail(1,"保存资源失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/resource/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = resourceService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "资源删除成功");
        }
        return ResponseDto.fail(1, "资源删除失败");
    }

}
