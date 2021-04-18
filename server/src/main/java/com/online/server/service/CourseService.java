package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.Course;
import com.online.server.domain.CourseExample;
import com.online.server.dto.CourseDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.CourseMapper;
import com.online.server.util.CopyUtil;
import com.online.server.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;



/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/9 14:24
 */
@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    /**
     * 分页查询
     * @param page
     * @param size
     * @param name
     * @return
     */
    public PageDto findAll(int page, int size, String name) {
        PageDto pageDto = new PageDto();
        pageDto.setPage(page);
        pageDto.setSize(size);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        courseExample.setOrderByClause("sort desc");
        if (StringUtils.isNotBlank(name)) {
            courseExample.createCriteria().andNameLike("%" + name + "%");
        }
        List<Course> courses = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course course : courses) {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto);
            courseDtoList.add(courseDto);
        }
        pageDto.setList(courseDtoList);
        return pageDto;
    }

    /**
     * 根据name查询
     * @param name
     * @return
     */
    public boolean findCourseByName(String name){
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andNameEqualTo(name);
        List<Course> courses = courseMapper.selectByExample(courseExample);
        return courses.size() >0;
    }

    public ResponseDto save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto, Course.class);
        if (StringUtils.isBlank(courseDto.getId())) {
            if(this.findCourseByName(courseDto.getName())){
                return ResponseDto.fail(1, "课程表名称重复,请重新输入!");
            }
            int insert = this.insert(course);
            if (insert > 0) {
                return ResponseDto.ok(0, "课程表添加成功");
            }
            return ResponseDto.fail(1, "课程表添加失败");
        } else {
            int update = this.update(course);
            if (update > 0) {
                return ResponseDto.ok(0, "课程表修改成功");
            }
            return ResponseDto.fail(1, "课程表修改失败");
        }
    }

    /**
     * 保存
     * @param course
     * @return
     */
    private int insert(Course course) {
        Date now = new Date();
        course.setCreateAt(now);
        course.setUpdateAt(now);
        course.setId(UuidUtil.getShortUuid());
        return courseMapper.insert(course);
    }

    /**
     * 修改
     * @param course
     * @return
     */
    private int update(Course course) {
        course.setUpdateAt(new Date());
        return courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return courseMapper.deleteByPrimaryKey(id);
    }




}