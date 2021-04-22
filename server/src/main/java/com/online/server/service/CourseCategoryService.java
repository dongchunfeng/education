package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.CourseCategory;
import com.online.server.domain.CourseCategoryExample;
import com.online.server.dto.*;
import com.online.server.mapper.CourseCategoryMapper;
import com.online.server.util.CopyUtil;
import com.online.server.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;




/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/9 14:24
 */
@Service
public class CourseCategoryService {

    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public PageDto findAll(int page, int size) {
        PageDto pageDto = new PageDto();
        pageDto.setPage(page);
        pageDto.setSize(size);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();

        List<CourseCategory> courseCategorys = courseCategoryMapper.selectByExample(courseCategoryExample);
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategorys);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto> courseCategoryDtoList = new ArrayList<>();
        for (CourseCategory courseCategory : courseCategorys) {
            CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
            BeanUtils.copyProperties(courseCategory, courseCategoryDto);
            courseCategoryDtoList.add(courseCategoryDto);
        }
        pageDto.setList(courseCategoryDtoList);
        return pageDto;
    }



    public ResponseDto save(CourseCategoryDto courseCategoryDto) {
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto, CourseCategory.class);
        if (StringUtils.isBlank(courseCategoryDto.getId())) {

            int insert = this.insert(courseCategory);
            if (insert > 0) {
                return ResponseDto.ok(0, "课程分类添加成功");
            }
            return ResponseDto.fail(1, "课程分类添加失败");
        } else {
            int update = this.update(courseCategory);
            if (update > 0) {
                return ResponseDto.ok(0, "课程分类修改成功");
            }
            return ResponseDto.fail(1, "课程分类修改失败");
        }
    }

    /**
     * 保存
     * @param courseCategory
     * @return
     */
    private int insert(CourseCategory courseCategory) {
        courseCategory.setId(UuidUtil.getShortUuid());
        return courseCategoryMapper.insert(courseCategory);
    }

    /**
     * 修改
     * @param courseCategory
     * @return
     */
    private int update(CourseCategory courseCategory) {
        return courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return courseCategoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void saveBatch(CourseDto courseDto){
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseDto.getId());
        courseCategoryMapper.deleteByExample(example);
        for (int i = 0; i < courseDto.getCategorys().size(); i++) {
            CategoryDto categoryDto = courseDto.getCategorys().get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseDto.getId());
            courseCategory.setCategoryId(categoryDto.getId());
            insert(courseCategory);
        }

    }

    public List<CourseCategoryDto> listByCourse(String courseId){
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategories = courseCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(courseCategories,CourseCategoryDto.class);
    }


}