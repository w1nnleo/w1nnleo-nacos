package com.w1nnleo.tclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.w1nnleo.exception.annotations.EnableGlobalException;

/**
 * @Author: w1nnleo
 * @date: 2023/2/17
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableGlobalException
public class TiaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiaClientApplication.class, args);
    }

}
