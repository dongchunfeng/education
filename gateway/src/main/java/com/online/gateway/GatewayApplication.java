package com.online.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Collections;

/**
 * @author MrDong
 */
@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class GatewayApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(GatewayApplication.class);
        Environment environment = app.run(args).getEnvironment();
        log.info("gateway 启动成功!");
        log.info("gateway: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));

    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(Boolean.TRUE);
        corsConfiguration.setAllowedOriginPatterns(Collections.unmodifiableList(Collections.singletonList("*")));
        corsConfiguration.setAllowedHeaders(Collections.unmodifiableList(Collections.singletonList("*")));
        corsConfiguration.setAllowedMethods(Collections.unmodifiableList(Collections.singletonList("*")));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }


}
