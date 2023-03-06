//package com.w1nnleo.gateway.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.web.server.WebFilter;
//
///**
// * @Author: w1nnleo
// * @date: 2023/2/27
// * @Description: 配置context path的自动路由
// */
//@Configuration
//@RefreshScope
//public class GatewayConfig {
//
//    @Value("${server.servlet.context-path}")
//    private String prefix;
//
//    /**
//     * 过滤 server.servlet.context-path 属性配置的项目路径，防止对后续路由策略产生影响，因为 gateway 网关不支持 servlet
//     */
//    @Bean
//    @Order(-1)
//    public WebFilter apiPrefixFilter() {
//        return (exchange, chain) ->
//        {
//            ServerHttpRequest request = exchange.getRequest();
//            String path = request.getURI().getRawPath();
//
//            path = path.startsWith(prefix) ? path.replaceFirst(prefix, "") : path;
//            ServerHttpRequest newRequest = request.mutate().path(path).build();
//
//            return chain.filter(exchange.mutate().request(newRequest).build());
//        };
//    }
//}