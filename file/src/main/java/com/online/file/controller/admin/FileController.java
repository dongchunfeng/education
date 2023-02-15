package com.online.file.controller.admin;

import com.online.server.dto.FileDto;
import com.online.server.dto.PageDto;
import com.online.server.dto.ResponseDto;
import com.online.server.service.FileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/14 10:51
 */
@RestController
@RequestMapping("/admin")
public class FileController {

    @Resource
    private FileService fileService;
    public static final String BUSINESS_NAME = "文件";

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(path = "/file/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size) {
        PageDto all = fileService.findAll(page, size);
        return new ResponseDto().ok(0, "文件列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param fileDto
     * @return
     */
    @RequestMapping(path = "/file/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody FileDto fileDto) {
        return fileService.save(fileDto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/file/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = fileService.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "文件删除成功");
        }
        return ResponseDto.fail(1, "文件删除失败");
    }

}
