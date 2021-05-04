package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.RoleUser;
import com.online.server.domain.RoleUserExample;
import com.online.server.dto.RoleUserDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.RoleUserMapper;
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
public class RoleUserService {

    @Resource
    private RoleUserMapper roleUserMapper;

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
        RoleUserExample roleUserExample = new RoleUserExample();

        List<RoleUser> roleUsers = roleUserMapper.selectByExample(roleUserExample);
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUsers);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleUserDto> roleUserDtoList = new ArrayList<>();
        for (RoleUser roleUser : roleUsers) {
            RoleUserDto roleUserDto = new RoleUserDto();
            BeanUtils.copyProperties(roleUser, roleUserDto);
            roleUserDtoList.add(roleUserDto);
        }
        pageDto.setList(roleUserDtoList);
        return pageDto;
    }


    public ResponseDto save(RoleUserDto roleUserDto) {
        RoleUser roleUser = CopyUtil.copy(roleUserDto, RoleUser.class);
        if (StringUtils.isBlank(roleUserDto.getId())) {

            int insert = this.insert(roleUser);
            if (insert > 0) {
                return ResponseDto.ok(0, "用户角色关联添加成功");
            }
            return ResponseDto.fail(1, "用户角色关联添加失败");
        } else {
            int update = this.update(roleUser);
            if (update > 0) {
                return ResponseDto.ok(0, "用户角色关联修改成功");
            }
            return ResponseDto.fail(1, "用户角色关联修改失败");
        }
    }

    /**
     * 保存
     * @param roleUser
     * @return
     */
    private int insert(RoleUser roleUser) {

        roleUser.setId(UuidUtil.getShortUuid());
        return roleUserMapper.insert(roleUser);
    }

    /**
     * 修改
     * @param roleUser
     * @return
     */
    private int update(RoleUser roleUser) {
        return roleUserMapper.updateByPrimaryKey(roleUser);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return roleUserMapper.deleteByPrimaryKey(id);
    }


    public List<RoleUserDto> findRoleUserByRoleId(String roleId){
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(roleUserExample);
        return CopyUtil.copyList(roleUsers,RoleUserDto.class);
    }

}