package com.online.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description 登录接口拦截
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/25 22:58
 */
@Component
@Slf4j
public class LoginAdminGatewayFilter implements GatewayFilter, Ordered {


    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
//        log.info("登录开始拦截, token:{}"+authorization);
        String path = exchange.getRequest().getURI().getPath();
        if (!path.contains("/admin")) {
            return chain.filter(exchange);
        }
        if (path.contains("/system/admin/user/login")
                || path.contains("/system/admin/user/logout")
                || path.contains("/system/admin/kaptcha")) {
            log.info("不需要控台登录验证: {}", path);
            return chain.filter(exchange);
        }

        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        log.info("控台验证登录开始, authorization: {}", authorization);
        if (authorization == null || authorization.isEmpty()) {
            log.info("authorization为空,请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        Object o = redisTemplate.opsForValue().get(authorization);

        if (o == null) {
            log.warn("authorization无效,请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } else {
            log.warn("已登录:{}", o);
            log.warn("接口权限校验,请求地址: {}", path);
            boolean exist = false;
            JSONObject loginUserDto = JSON.parseObject(String.valueOf(o));
            JSONArray requests = loginUserDto.getJSONArray("requests");
            for (int i = 0; i < requests.size(); i++) {
                String request = (String)requests.get(i);
                if(path.contains(request)){
                    exist=true;
                    break;
                }
            }
            if(exist){
                log.info("权限校验通过");
            }else{
                log.info("权限校验未通过");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
