package com.online.business.controller.admin;

import com.online.server.domain.Chapter;
import com.online.server.dto.ChapterDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.QueryChapterDto;
import com.online.server.dto.ResponseDto;
import com.online.server.service.ChapterService;
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
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping(path = "/chapter/{page}/{size}",method = RequestMethod.GET)
    public ResponseDto chapter(@PathVariable int page,@PathVariable int size, QueryChapterDto qs){
        PageDto all = chapterService.findAll(page, size, qs.getDaname());
        return new ResponseDto().ok(0,"大章列表查询成功",all);
    }

    @RequestMapping(path = "/chapter/add",method = RequestMethod.POST)
    public ResponseDto chapter(@RequestBody ChapterDto chapterDto){
        return chapterService.save(chapterDto);
    }

    @RequestMapping(path = "/chapter/delete/{id}",method = RequestMethod.GET)
    public ResponseDto chapter(@PathVariable String id){
        int i = chapterService.delete(id);
        if(i>0){
            return ResponseDto.ok(0,"大章删除成功");
        }
        return ResponseDto.fail(1,"大章删除失败");
    }

}
