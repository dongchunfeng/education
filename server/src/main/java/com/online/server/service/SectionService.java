package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.Section;
import com.online.server.domain.SectionExample;
import com.online.server.dto.QuerySectionDto;
import com.online.server.dto.SectionDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.enums.SectionChargeEnum;
import com.online.server.mapper.SectionMapper;
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
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;

    /**
     * 分页查询
     * @param page
     * @param size
     * @param qs
     * @return
     */
    public PageDto findAll(int page, int size, QuerySectionDto qs) {
        PageDto pageDto = new PageDto();
        pageDto.setPage(page);
        pageDto.setSize(size);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        sectionExample.setOrderByClause("sort desc");
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if (StringUtils.isNotBlank(qs.getTitle())) {
            criteria.andTitleLike("%" + qs.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(qs.getTitle())) {
            criteria.andCourseIdEqualTo( qs.getCourseId());
        }
        if (StringUtils.isNotBlank(qs.getChapterId())) {
            criteria.andChapterIdEqualTo( qs.getChapterId());
        }
        List<Section> sections = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sections);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = new ArrayList<>();
        for (Section section : sections) {
            SectionDto sectionDto = new SectionDto();
            BeanUtils.copyProperties(section, sectionDto);
            sectionDtoList.add(sectionDto);
        }
        pageDto.setList(sectionDtoList);
        return pageDto;
    }

    /**
     * 根据title查询
     * @param title
     * @return
     */
    public boolean findSectionByTitle(String title){
        SectionExample sectionExample = new SectionExample();
        sectionExample.createCriteria().andTitleEqualTo(title);
        List<Section> sections = sectionMapper.selectByExample(sectionExample);
        return sections.size() >0;
    }

    public ResponseDto save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isBlank(sectionDto.getId())) {
            if(this.findSectionByTitle(sectionDto.getTitle())){
                return ResponseDto.fail(1, "小节名称重复,请重新输入!");
            }
            int insert = this.insert(section);
            if (insert > 0) {
                return ResponseDto.ok(0, "小节添加成功");
            }
            return ResponseDto.fail(1, "小节添加失败");
        } else {
            int update = this.update(section);
            if (update > 0) {
                return ResponseDto.ok(0, "小节修改成功");
            }
            return ResponseDto.fail(1, "小节修改失败");
        }
    }

    /**
     * 保存
     * @param section
     * @return
     */
    private int insert(Section section) {
        Date now = new Date();
        section.setCreateAt(now);
        section.setUpdateAt(now);
        section.setId(UuidUtil.getShortUuid());
        section.setCharge(SectionChargeEnum.CHARGE.getCode());
        return sectionMapper.insert(section);
    }

    /**
     * 修改
     * @param section
     * @return
     */
    private int update(Section section) {
        section.setUpdateAt(new Date());
        return sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return sectionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询某一课程下的所有小节
     */
    public List<SectionDto> listByCourse(String courseId) {
        SectionExample example = new SectionExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<Section> sectionList = sectionMapper.selectByExample(example);
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        return sectionDtoList;
    }


}
