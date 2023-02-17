package com.w1nnleo.tclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.w1nnleo.base.entity.R;
import com.w1nnleo.base.enums.CommonEnum;
import com.w1nnleo.exception.entity.BusinessException;

/**
 * @Author: w1nnleo
 * @date: 2023/2/13
 * @Description:
 */
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
        return R.ok("hi");
    }

}
