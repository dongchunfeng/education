package com.online.business.controller.admin;

import com.online.server.dto.CategoryDto;
import com.online.server.dto.QueryCategoryDto;
import com.online.server.dto.ResponseDto;
import com.online.server.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/14 10:51
 */
@RestController
@RequestMapping("/admin")
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    public static final String BUSINESS_NAME = "分类表";

    /**
     * 分页查询
     * @param qs
     * @return
     */
    @RequestMapping(path = "/category", method = RequestMethod.GET)
    public ResponseDto list( QueryCategoryDto qs) {
        List<CategoryDto> all = categoryService.findAll(qs.getName());
        return new ResponseDto().ok(0, "分类表列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param categoryDto
     * @return
     */
    @RequestMapping(path = "/category/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/category/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = categoryService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "分类表删除成功");
        }
        return ResponseDto.fail(1, "分类表删除失败");
    }

}
