

package com.w1nnleo.gateway.config;

import org.springframework.boot.CommandLineRunner;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.w1nnleo.gateway.handler.SentinelBlockRequestHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: w1nnleo
 * @date: 2023/2/20
 * @Description: 自定义异常返回
 */
@Slf4j
//@Configuration
public class InitConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化限流handler");
        GatewayCallbackManager.setBlockHandler(new SentinelBlockRequestHandler());
    }
}

