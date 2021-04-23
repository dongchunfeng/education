package com.online.file.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author MrDong
 */
@SpringBootApplication
@EnableEurekaClient
@Slf4j
@ComponentScan("com.online")
@MapperScan("com.online.server.mapper")
public class FileApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(FileApplication.class);
        Environment environment = app.run(args).getEnvironment();
        log.info("file 启动成功!");
        log.info("file: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));

    }


}
