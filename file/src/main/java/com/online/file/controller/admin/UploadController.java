package com.online.file.controller.admin;

import com.online.server.dto.FileDto;
import com.online.server.dto.ResponseDto;
import com.online.server.service.FileService;
import com.online.server.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/23 22:48
 */
@RestController
@RequestMapping(value = "/admin")
@Slf4j
public class UploadController {

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String fileDomain;
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        log.info("文件上传开始 : {}", file);
        log.info(file.getOriginalFilename());
        log.info(String.valueOf(file.getSize()));

        String fileName = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();//12.jpg
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        String path = "teacher/" + key + "." + suffix;
        String fullPath = filePath + path;
        File dest = new File(fullPath);
        file.transferTo(dest);

        log.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(fileName);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse("");
        fileService.save(fileDto);

        return new ResponseDto().ok(0, "文件上传成功", fileDomain +path);
    }

}
