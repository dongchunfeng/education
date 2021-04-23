package com.online.business.controller.admin;

import com.online.server.dto.*;
import com.online.server.service.TeacherService;
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
public class TeacherController {

    @Resource
    private TeacherService teacherService;
    public static final String BUSINESS_NAME = "讲师";

    /**
     * 查询所有讲师
     * @return
     */
    @RequestMapping(path = "/teacher/getTeacherList", method = RequestMethod.GET)
    public ResponseDto list() {
        List<TeacherDto> all = teacherService.all();
        return new ResponseDto().ok(0, "讲师列表查询成功", all);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @param qs
     * @return
     */
    @RequestMapping(path = "/teacher/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size, QueryTeacherDto qs) {
        PageDto all = teacherService.findAll(page, size, qs.getName());
        return new ResponseDto().ok(0, "讲师列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param teacherDto
     * @return
     */
    @RequestMapping(path = "/teacher/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody TeacherDto teacherDto) {
        return teacherService.save(teacherDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/teacher/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = teacherService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "讲师删除成功");
        }
        return ResponseDto.fail(1, "讲师删除失败");
    }

}
