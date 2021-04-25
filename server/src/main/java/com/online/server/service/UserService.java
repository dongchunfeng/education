package com.online.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.User;
import com.online.server.domain.UserExample;
import com.online.server.dto.LoginUserDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.dto.UserDto;
import com.online.server.exception.BusinessException;
import com.online.server.exception.BusinessExceptionCode;
import com.online.server.mapper.UserMapper;
import com.online.server.util.CopyUtil;
import com.online.server.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
@Slf4j
public class UserService {

    @Resource
    private UserMapper userMapper;

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
        UserExample userExample = new UserExample();
        if (StringUtils.isNotBlank(name)) {
            userExample.createCriteria().andNameLike("%" + name + "%");
        }
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtoList.add(userDto);
        }
        pageDto.setList(userDtoList);
        return pageDto;
    }

    /**
     * 根据name查询
     * @param name
     * @return
     */
    public boolean findUserByName(String name){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() >0;
    }

    /**
     * 根据login_name查询
     * @param loginName
     * @return
     */
    public boolean findUserByLoginName(String loginName){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() >0;
    }

    public ResponseDto save(UserDto userDto) {
        User user = CopyUtil.copy(userDto, User.class);
        if (StringUtils.isBlank(userDto.getId())) {
            if(this.findUserByLoginName(userDto.getLoginName())){
                return ResponseDto.fail(1, "登录名称重复,请重新输入!");
            }
            if(this.findUserByName(userDto.getName())){
                return ResponseDto.fail(1, "用户名称重复,请重新输入!");
            }
            int insert = this.insert(user);
            if (insert > 0) {
                return ResponseDto.ok(0, "用户添加成功");
            }
            return ResponseDto.fail(1, "用户添加失败");
        } else {
            int update = this.update(user);
            if (update > 0) {
                return ResponseDto.ok(0, "用户修改成功");
            }
            return ResponseDto.fail(1, "用户修改失败");
        }
    }

    /**
     * 保存
     * @param user
     * @return
     */
    private int insert(User user) {

        user.setId(UuidUtil.getShortUuid());
        return userMapper.insert(user);
    }

    /**
     * 修改
     * @param user
     * @return
     */
    private int update(User user) {
        user.setPassword(null);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int savePassword(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public User findByLoginName(String loginName){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        return users.get(0);
    }

    public LoginUserDto login(UserDto userDto){
        if(StringUtils.isBlank(userDto.getLoginName())){
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_NOT_NULL);
        }
        if(StringUtils.isBlank(userDto.getPassword())){
            throw new BusinessException(BusinessExceptionCode.LOGIN_PASSWORD_NOT_NULL);
        }
        User user = findByLoginName(userDto.getLoginName());
        if(user==null){
            log.info("用户名不存在: {}",userDto.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }else{
            if(user.getPassword().equals(userDto.getPassword())){
                //登录成功
                return CopyUtil.copy(user, LoginUserDto.class);
            }else {
                log.info("密码不对，输入密码: {}, 数据库密码: {}",userDto.getPassword(),user.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

}