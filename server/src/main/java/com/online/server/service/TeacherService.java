package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.Teacher;
import com.online.server.domain.TeacherExample;
import com.online.server.dto.TeacherDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.TeacherMapper;
import com.online.server.util.CopyUtil;
import com.online.server.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

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
        TeacherExample teacherExample = new TeacherExample();
        if (StringUtils.isNotBlank(name)) {
            teacherExample.createCriteria().andNameLike("%" + name + "%");
        }
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher, teacherDto);
            teacherDtoList.add(teacherDto);
        }
        pageDto.setList(teacherDtoList);
        return pageDto;
    }

    public List<TeacherDto> all(){
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        return CopyUtil.copyList(teachers,TeacherDto.class);
    }

    /**
     * 根据name查询
     * @param name
     * @return
     */
    public boolean findTeacherByName(String name){
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria().andNameEqualTo(name);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        return teachers.size() >0;
    }

    public ResponseDto save(TeacherDto teacherDto) {
        Teacher teacher = CopyUtil.copy(teacherDto, Teacher.class);
        if (StringUtils.isBlank(teacherDto.getId())) {
            if(this.findTeacherByName(teacherDto.getName())){
                return ResponseDto.fail(1, "讲师名称重复,请重新输入!");
            }
            int insert = this.insert(teacher);
            if (insert > 0) {
                return ResponseDto.ok(0, "讲师添加成功");
            }
            return ResponseDto.fail(1, "讲师添加失败");
        } else {
            int update = this.update(teacher);
            if (update > 0) {
                return ResponseDto.ok(0, "讲师修改成功");
            }
            return ResponseDto.fail(1, "讲师修改失败");
        }
    }

    /**
     * 保存
     * @param teacher
     * @return
     */
    private int insert(Teacher teacher) {

        teacher.setId(UuidUtil.getShortUuid());
        return teacherMapper.insert(teacher);
    }

    /**
     * 修改
     * @param teacher
     * @return
     */
    private int update(Teacher teacher) {
        return teacherMapper.updateByPrimaryKey(teacher);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return teacherMapper.deleteByPrimaryKey(id);
    }




}