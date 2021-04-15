package com.online.business.controller;

import com.online.server.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一异常处理
 *
 * @author MrDong
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseDto handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("服务器发生异常: " + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            log.error(element.toString());
        }

        return ResponseDto.fail(1, "服务器异常");
    }


}
