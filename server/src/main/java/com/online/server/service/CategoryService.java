package com.online.server.service;

import com.online.server.domain.Category;
import com.online.server.domain.CategoryExample;
import com.online.server.dto.CategoryDto;
import com.online.server.dto.ResponseDto;
import com.online.server.mapper.CategoryMapper;
import com.online.server.util.CopyUtil;
import com.online.server.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;




/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/9 14:24
 */
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 分页查询
     * @param name
     * @return
     */
    public List<CategoryDto> findAll( String name) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort desc");
        if (StringUtils.isNotBlank(name)) {
            categoryExample.createCriteria().andNameLike("%" + name + "%");
        }
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtos = CopyUtil.copyList(categorys, CategoryDto.class);
        return categoryDtos;
    }

    /**
     * 根据name查询
     * @param name
     * @return
     */
    public boolean findCategoryByName(String name){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andNameEqualTo(name);
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        return categorys.size() >0;
    }

    public ResponseDto save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto, Category.class);
        if (StringUtils.isBlank(categoryDto.getId())) {
            if(this.findCategoryByName(categoryDto.getName())){
                return ResponseDto.fail(1, "分类表名称重复,请重新输入!");
            }
            int insert = this.insert(category);
            if (insert > 0) {
                return ResponseDto.ok(0, "分类表添加成功");
            }
            return ResponseDto.fail(1, "分类表添加失败");
        } else {
            int update = this.update(category);
            if (update > 0) {
                return ResponseDto.ok(0, "分类表修改成功");
            }
            return ResponseDto.fail(1, "分类表修改失败");
        }
    }

    /**
     * 保存
     * @param category
     * @return
     */
    private int insert(Category category) {

        category.setId(UuidUtil.getShortUuid());
        return categoryMapper.insert(category);
    }

    /**
     * 修改
     * @param category
     * @return
     */
    private int update(Category category) {
        return categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 删除
     */
    public int delete(String id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }




}