package com.online.${module}.controller.admin;

import com.online.server.domain.${Domain};
import com.online.server.dto.${Domain}Dto;
import com.online.server.dto.PageDto;
import com.online.server.dto.Query${Domain}Dto;
import com.online.server.dto.ResponseDto;
import com.online.server.service.${Domain}Service;
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
public class ${Domain}Controller {

    @Resource
    private ${Domain}Service ${domain}Service;
    public static final String BUSINESS_NAME = "${tableNameCn}";

    /**
     * 分页查询
     * @param page
     * @param size
     * @param qs
     * @return
     */
    @RequestMapping(path = "/${domain}/{page}/{size}", method = RequestMethod.GET)
    public ResponseDto list(@PathVariable int page, @PathVariable int size) {
        PageDto all = ${domain}Service.findAll(page, size);
        return new ResponseDto().ok(0, "${tableNameCn}列表查询成功", all);
    }

    /**
     * 保存 id有值更新 无值时新增
     * @param ${domain}Dto
     * @return
     */
    @RequestMapping(path = "/${domain}/add", method = RequestMethod.POST)
    public ResponseDto save(@RequestBody ${Domain}Dto ${domain}Dto) {
        return ${domain}Service.save(${domain}Dto);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(path = "/${domain}/delete/{id}", method = RequestMethod.GET)
    public ResponseDto delete(@PathVariable String id) {
        int i = ${domain}Service.delete(id);
        if (i > 0) {
            return ResponseDto.ok(0, "${tableNameCn}删除成功");
        }
        return ResponseDto.fail(1, "${tableNameCn}删除失败");
    }

}
