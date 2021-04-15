package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.Chapter;
import com.online.server.domain.ChapterExample;
import com.online.server.dto.ChapterDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.ChapterMapper;
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
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

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
        ChapterExample chapterExample = new ChapterExample();
        if (StringUtils.isNotBlank(name)) {
            chapterExample.createCriteria().andNameLike("%" + name + "%");
        }
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapters);
        pageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        for (Chapter chapter : chapters) {
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            chapterDtoList.add(chapterDto);
        }
        pageDto.setList(chapterDtoList);
        return pageDto;
    }

    /**
     * 根据name查询
     * @param name
     * @return
     */
    public boolean findChapterByName(String name){
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andNameEqualTo(name);
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        return chapters.size() >0;
    }

    public ResponseDto save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isBlank(chapterDto.getId())) {
            if(this.findChapterByName(chapterDto.getName())){
                return ResponseDto.fail(1, "大章名称重复,请重新输入!");
            }
            int insert = this.insert(chapter);
            if (insert > 0) {
                return ResponseDto.ok(0, "大章添加成功");
            }
            return ResponseDto.fail(1, "大章添加失败");
        } else {
            int update = this.update(chapter);
            if (update > 0) {
                return ResponseDto.ok(0, "大章修改成功");
            }
            return ResponseDto.fail(1, "大章修改失败");
        }
    }

    /**
     * 保存
     * @param chapter
     * @return
     */
    private int insert(Chapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        return chapterMapper.insert(chapter);
    }

    /**
     * 修改
     * @param chapter
     * @return
     */
    private int update(Chapter chapter) {
        return chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return chapterMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询某一课程下的所有大章
     */
    public List<ChapterDto> listByCourse(String courseId) {
        ChapterExample example = new ChapterExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        return chapterDtoList;
    }


}
