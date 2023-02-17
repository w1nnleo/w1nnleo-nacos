package com.w1nnleo.client.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.w1nnleo.base.entity.R;

/**
 * @Author: w1nnleo
 * @date: 2023/2/17
 * @Description:
 */
@FeignClient(value = "tia-client")
public interface HelloService {

    @GetMapping("/tiaClient/hello")
    R<?> hello();

    @GetMapping("/tiaClient/hi")
    R<?> hi();
}
