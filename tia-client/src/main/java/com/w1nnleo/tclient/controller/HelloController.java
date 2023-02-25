package com.w1nnleo.tclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.w1nnleo.base.entity.R;
import com.w1nnleo.base.enums.CommonEnum;
import com.w1nnleo.exception.entity.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: w1nnleo
 * @date: 2023/2/13
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/")
public class HelloController {


    @GetMapping("/hello")
    public R<?> hello() {
        throw new BusinessException(CommonEnum.FAIL.getCode(), CommonEnum.FAIL.getMsg());
//        return R.ok("hello");
    }

    @GetMapping("hi")
    public R<?> hi() {
        log.info("w1nnleo client hi");
        return R.ok("hi");
    }

}
