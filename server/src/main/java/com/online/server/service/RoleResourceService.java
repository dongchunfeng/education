package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.RoleResource;
import com.online.server.domain.RoleResourceExample;
import com.online.server.dto.RoleResourceDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.RoleResourceMapper;
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
public class RoleResourceService {

    @Resource
    private RoleResourceMapper roleResourceMapper;

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
        RoleResourceExample roleResourceExample = new RoleResourceExample();

        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResources);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleResourceDto> roleResourceDtoList = new ArrayList<>();
        for (RoleResource roleResource : roleResources) {
            RoleResourceDto roleResourceDto = new RoleResourceDto();
            BeanUtils.copyProperties(roleResource, roleResourceDto);
            roleResourceDtoList.add(roleResourceDto);
        }
        pageDto.setList(roleResourceDtoList);
        return pageDto;
    }


    public ResponseDto save(RoleResourceDto roleResourceDto) {
        RoleResource roleResource = CopyUtil.copy(roleResourceDto, RoleResource.class);
        if (StringUtils.isBlank(roleResourceDto.getId())) {

            int insert = this.insert(roleResource);
            if (insert > 0) {
                return ResponseDto.ok(0, "角色资源关联添加成功");
            }
            return ResponseDto.fail(1, "角色资源关联添加失败");
        } else {
            int update = this.update(roleResource);
            if (update > 0) {
                return ResponseDto.ok(0, "角色资源关联修改成功");
            }
            return ResponseDto.fail(1, "角色资源关联修改失败");
        }
    }

    /**
     * 保存
     * @param roleResource
     * @return
     */
    private int insert(RoleResource roleResource) {

        roleResource.setId(UuidUtil.getShortUuid());
        return roleResourceMapper.insert(roleResource);
    }

    /**
     * 修改
     * @param roleResource
     * @return
     */
    private int update(RoleResource roleResource) {
        return roleResourceMapper.updateByPrimaryKey(roleResource);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return roleResourceMapper.deleteByPrimaryKey(id);
    }




}