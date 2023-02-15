package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.File;
import com.online.server.domain.FileExample;
import com.online.server.dto.FileDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.FileMapper;
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
public class FileService {

    @Resource
    private FileMapper fileMapper;

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
        FileExample fileExample = new FileExample();

        List<File> files = fileMapper.selectByExample(fileExample);
        PageInfo<File> pageInfo = new PageInfo<>(files);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDto> fileDtoList = new ArrayList<>();
        for (File file : files) {
            FileDto fileDto = new FileDto();
            BeanUtils.copyProperties(file, fileDto);
            fileDtoList.add(fileDto);
        }
        pageDto.setList(fileDtoList);
        return pageDto;
    }


    public ResponseDto save(FileDto fileDto) {
        File file = CopyUtil.copy(fileDto, File.class);
        if (StringUtils.isBlank(fileDto.getId())) {

            int insert = this.insert(file);
            if (insert > 0) {
                return ResponseDto.ok(0, "文件添加成功");
            }
            return ResponseDto.fail(1, "文件添加失败");
        } else {
            int update = this.update(file);
            if (update > 0) {
                return ResponseDto.ok(0, "文件修改成功");
            }
            return ResponseDto.fail(1, "文件修改失败");
        }
    }

    /**
     * 保存
     * @param file
     * @return
     */
    private int insert(File file) {
        Date now = new Date();

        file.setCreateAt(now);
        file.setUpdateAt(now);
        file.setId(UuidUtil.getShortUuid());
        return fileMapper.insert(file);
    }

    /**
     * 修改
     * @param file
     * @return
     */
    private int update(File file) {
        file.setUpdateAt(new Date());
        return fileMapper.updateByPrimaryKey(file);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return fileMapper.deleteByPrimaryKey(id);
    }




}