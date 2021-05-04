package com.online.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.online.server.domain.Resource;
import com.online.server.domain.ResourceExample;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResourceDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.ResourceMapper;
import com.online.server.util.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
public class ResourceService {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    /**
     * 分页查询
     *
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
        ResourceExample resourceExample = new ResourceExample();
        if (StringUtils.isNotBlank(name)) {
            resourceExample.createCriteria().andNameLike("%" + name + "%");
        }
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resource, resourceDto);
            resourceDtoList.add(resourceDto);
        }
        pageDto.setList(resourceDtoList);
        return pageDto;
    }

    /**
     * 按约定将列表转换成树
     *
     * @return
     */
    public List<ResourceDto> findAll() {
        ResourceExample resourceExample = new ResourceExample();
        resourceExample.setOrderByClause("id asc");
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resource, resourceDto);
            resourceDtoList.add(resourceDto);
        }
        for (int i = resourceDtoList.size()-1; i >=0 ; i--) {
            //当前要移动的记录
            ResourceDto child = resourceDtoList.get(i);

            //如果当前节点没有父节点就不用往下了
            if(StringUtils.isBlank(child.getParent())){
                continue;
            }

            //查找父节点
            for (int j = i-1; j >=0; j--) {
                ResourceDto parent = resourceDtoList.get(j);
                if(child.getParent().equals(parent.getId())){
                    if(CollectionUtils.isEmpty(parent.getChildren())){
                        parent.setChildren(new ArrayList<>());
                    }

                    //添加到最前面
                    parent.getChildren().add(0,child);
                    //子节点找到父节点后删除列表中的子节点
                    resourceDtoList.remove(child);

                }
            }

        }
        return resourceDtoList;
    }

    /**
     * 根据name查询
     *
     * @param name
     * @return
     */
    public boolean findResourceByName(String name) {
        ResourceExample resourceExample = new ResourceExample();
        resourceExample.createCriteria().andNameEqualTo(name);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        return resources.size() > 0;
    }

    public ResponseDto save(ResourceDto resourceDto) {
        Resource resource = CopyUtil.copy(resourceDto, Resource.class);
        if (StringUtils.isBlank(resourceDto.getId())) {
            if (this.findResourceByName(resourceDto.getName())) {
                return ResponseDto.fail(1, "资源名称重复,请重新输入!");
            }
            int insert = this.insert(resource);
            if (insert > 0) {
                return ResponseDto.ok(0, "资源添加成功");
            }
            return ResponseDto.fail(1, "资源添加失败");
        } else {
            int update = this.update(resource);
            if (update > 0) {
                return ResponseDto.ok(0, "资源修改成功");
            }
            return ResponseDto.fail(1, "资源修改失败");
        }
    }

    /**
     * 保存资源树
     *
     * @param json
     * @return
     */
    @Transactional
    public int saveJson(String json) {
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        log.info("共{}条", list.size());

        resourceMapper.deleteByExample(null);
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            j = this.insert(CopyUtil.copy(list.get(i), Resource.class));
        }
        return j;
    }

    public void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }

    /**
     * 保存
     *
     * @param resource
     * @return
     */
    private int insert(Resource resource) {
        return resourceMapper.insert(resource);
    }

    /**
     * 修改
     *
     * @param resource
     * @return
     */
    private int update(Resource resource) {
        return resourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return resourceMapper.deleteByPrimaryKey(id);
    }


}