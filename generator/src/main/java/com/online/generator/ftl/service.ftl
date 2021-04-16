package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.${Domain};
import com.online.server.domain.${Domain}Example;
import com.online.server.dto.${Domain}Dto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.${Domain}Mapper;
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
public class ${Domain}Service {

    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    /**
     * 分页查询
     * @param page
     * @param size
     * @param ${searchname}
     * @return
     */
    public PageDto findAll(int page, int size, String ${searchname}) {
        PageDto pageDto = new PageDto();
        pageDto.setPage(page);
        pageDto.setSize(size);
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        if (StringUtils.isNotBlank(${searchname})) {
            ${domain}Example.createCriteria().and${Searchname}Like("%" + ${searchname} + "%");
        }
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> ${domain}DtoList = new ArrayList<>();
        for (${Domain} ${domain} : ${domain}s) {
            ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
            BeanUtils.copyProperties(${domain}, ${domain}Dto);
            ${domain}DtoList.add(${domain}Dto);
        }
        pageDto.setList(${domain}DtoList);
        return pageDto;
    }

    /**
     * 根据${searchname}查询
     * @param ${searchname}
     * @return
     */
    public boolean find${Domain}By${Searchname}(String ${searchname}){
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.createCriteria().and${Searchname}EqualTo(${searchname});
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        return ${domain}s.size() >0;
    }

    public ResponseDto save(${Domain}Dto ${domain}Dto) {
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto, ${Domain}.class);
        if (StringUtils.isBlank(${domain}Dto.getId())) {
            if(this.find${Domain}By${Searchname}(${domain}Dto.get${Searchname}())){
                return ResponseDto.fail(1, "${tableNameCn}名称重复,请重新输入!");
            }
            int insert = this.insert(${domain});
            if (insert > 0) {
                return ResponseDto.ok(0, "${tableNameCn}添加成功");
            }
            return ResponseDto.fail(1, "${tableNameCn}添加失败");
        } else {
            int update = this.update(${domain});
            if (update > 0) {
                return ResponseDto.ok(0, "${tableNameCn}修改成功");
            }
            return ResponseDto.fail(1, "${tableNameCn}修改失败");
        }
    }

    /**
     * 保存
     * @param ${domain}
     * @return
     */
    private int insert(${Domain} ${domain}) {
        ${domain}.setId(UuidUtil.getShortUuid());
        return ${domain}Mapper.insert(${domain});
    }

    /**
     * 修改
     * @param ${domain}
     * @return
     */
    private int update(${Domain} ${domain}) {
        return ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return ${domain}Mapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询某一课程下的所有${tableNameCn}
     */
    public List<${Domain}Dto> listByCourse(String courseId) {
        ${Domain}Example example = new ${Domain}Example();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(example);
        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}List, ${Domain}Dto.class);
        return ${domain}DtoList;
    }


}
