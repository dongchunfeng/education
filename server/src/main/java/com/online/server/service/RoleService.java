package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.Role;
import com.online.server.domain.RoleExample;
import com.online.server.dto.RoleDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.RoleMapper;
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
public class RoleService {

    @Resource
    private RoleMapper roleMapper;

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
        RoleExample roleExample = new RoleExample();

        List<Role> roles = roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtoList.add(roleDto);
        }
        pageDto.setList(roleDtoList);
        return pageDto;
    }


    public ResponseDto save(RoleDto roleDto) {
        Role role = CopyUtil.copy(roleDto, Role.class);
        if (StringUtils.isBlank(roleDto.getId())) {

            int insert = this.insert(role);
            if (insert > 0) {
                return ResponseDto.ok(0, "角色添加成功");
            }
            return ResponseDto.fail(1, "角色添加失败");
        } else {
            int update = this.update(role);
            if (update > 0) {
                return ResponseDto.ok(0, "角色修改成功");
            }
            return ResponseDto.fail(1, "角色修改失败");
        }
    }

    /**
     * 保存
     * @param role
     * @return
     */
    private int insert(Role role) {

        role.setId(UuidUtil.getShortUuid());
        return roleMapper.insert(role);
    }

    /**
     * 修改
     * @param role
     * @return
     */
    private int update(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return roleMapper.deleteByPrimaryKey(id);
    }




}