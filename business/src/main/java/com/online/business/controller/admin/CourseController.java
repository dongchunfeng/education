package com.online.business.controller.admin;

import com.online.server.dto.*;
import com.online.server.service.CourseCategoryService;
import com.online.server.service.CourseService;
import org.apache.commons.lang3.StringUtils;
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
public class CourseController {

    @Resource
    private CourseService courseService;
    public static final String BUSINESS_NAME = "课程表";
    @Autowired
    private CourseCategoryService courseCategoryService;
    /**
     * 分页查询
     * @param page
     * @param size
     * @param qs
     * @return
     */
    @RequestMapping(path = "/course/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size, QueryCourseDto qs) {
        PageDto all = courseService.findAll(page, size, qs.getName());
        return new ResponseDto().ok(0, "课程表列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param courseDto
     * @return
     */
    @RequestMapping(path = "/course/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        return courseService.save(courseDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/course/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = courseService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "课程表删除成功");
        }
        return ResponseDto.fail(1, "课程表删除失败");
    }

    @RequestMapping(path = "/course/list-category/{courseId}", method = RequestMethod.GET)
    public ResponseDto listCategory(@PathVariable String courseId) {
        List<CourseCategoryDto> courseCategoryDtos = courseCategoryService.listByCourse(courseId);
        return new ResponseDto().ok(0, "查询课程下的分类成功", courseCategoryDtos);
    }

    @RequestMapping(path = "/course/findCourseContent/{courseId}", method = RequestMethod.GET)
    public ResponseDto findCourseContent(@PathVariable String courseId) {
        CourseContentDto content = courseService.findContent(courseId);
        ResponseDto responseDto = new ResponseDto().ok(0, "查询课程的内容成功", content);
        if(responseDto.getData()==null){
            return ResponseDto.fail(0,"课程内容为空!");
        }
        return new ResponseDto().ok(0, "查询课程的内容成功", content);
    }

    @RequestMapping(path = "/course/saveContent", method = RequestMethod.POST)
    public ResponseDto findCourseContent(@RequestBody CourseContentDto courseContentDto) {
        if(StringUtils.isBlank(courseContentDto.getId())||StringUtils.isBlank(courseContentDto.getContent())){
            return ResponseDto.fail(0,"数据为空,请重试!");
        }
        int i = courseService.saveContent(courseContentDto);
        return ResponseDto.ok(0, "添加课程的内容成功");
    }


}
