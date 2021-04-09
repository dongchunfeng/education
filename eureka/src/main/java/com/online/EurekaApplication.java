package com.online;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;

/**
 * @author MrDong
 */
@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class EurekaApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(EurekaApplication.class);
        Environment environment = app.run(args).getEnvironment();
        log.info("启动成功!");
        log.info("Eureka: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));

    }


}
