package com.w1nnleo.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @Author: w1nnleo
 * @date: 2023/2/17
 * @Description:动态路由配置
 */
@RefreshScope
@Component
@Slf4j
public class DynamicRouteConfig implements ApplicationEventPublisherAware {

    /**
     * nacos 配置dataId
     */
    @Value("${config.dynamic.route.data-id:gateway-routes}")
    private String dataId = "gateway-router";
    /**
     * nacos 配置group
     */
    @Value("${config.dynamic.route.group:DEFAULT_GROUP}")
    private String group = "DEFAULT_GROUP";

    /**
     * nacos 配置地址
     */
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    /**
     * nacos 配置空间
     */
    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;


    private ApplicationEventPublisher applicationEventPublisher;

    private static final List<String> ROUTE_LIST = new ArrayList<>();

    @PostConstruct
    public void dynamicRouteByNacosListener() {
        try {
            //获取配置，如果有空间指定，需要传递空间参数否则获取不了config信息
            ConfigService configService;
            if (Objects.nonNull(namespace)) {
                Properties properties = new Properties();
                properties.put("serverAddr", serverAddr);
                properties.put("namespace", namespace);
                configService = NacosFactory.createConfigService(properties);
            }else {
                configService = NacosFactory.createConfigService(serverAddr);
            }
            // 程序首次启动, 并加载初始化路由配置
            String initConfigInfo = configService.getConfig(dataId, group, 5000);
            this.addAndPublishBatchRoute(initConfigInfo);
            //添加监听路由变化
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    clearRoute();
                    try {
                        List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
                        for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                            addRoute(routeDefinition);
                        }
                        publish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 批量 添加及发布 路由
     *
     * @param configInfo 配置文件字符串, 必须为json array格式
     */
    private void addAndPublishBatchRoute(String configInfo) {
        try {
            clearRoute();
            List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
            for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                addRoute(routeDefinition);
            }
            publish();
            log.info("Dynamic config gateway route finished. {}", JSON.toJSONString(gatewayRouteDefinitions));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除路由信息
     */
    private void clearRoute() {
        for (String id : ROUTE_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    /**
     * 添加路由
     *
     * @param definition
     */
    private void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            ROUTE_LIST.add(definition.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}