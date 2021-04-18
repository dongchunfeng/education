package com.online.business.controller.admin;

import com.online.server.domain.Section;
import com.online.server.dto.SectionDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.QuerySectionDto;
import com.online.server.dto.ResponseDto;
import com.online.server.service.SectionService;
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
public class SectionController {

    @Resource
    private SectionService sectionService;
    public static final String BUSINESS_NAME = "小节";

    /**
     * 分页查询
     * @param page
     * @param size
     * @param qs
     * @return
     */
    @RequestMapping(path = "/section/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size, QuerySectionDto qs) {
        PageDto all = sectionService.findAll(page, size, qs.getTitle());
        return new ResponseDto().ok(0, "小节列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param sectionDto
     * @return
     */
    @RequestMapping(path = "/section/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody SectionDto sectionDto) {
        return sectionService.save(sectionDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/section/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = sectionService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "小节删除成功");
        }
        return ResponseDto.fail(1, "小节删除失败");
    }

}
