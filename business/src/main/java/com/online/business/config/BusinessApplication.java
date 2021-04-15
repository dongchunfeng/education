package com.online.business.config;

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
public class BusinessApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(BusinessApplication.class);
        Environment environment = app.run(args).getEnvironment();
        log.info("business 启动成功!");
        log.info("business: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));

    }


}
