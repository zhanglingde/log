package com.log.log.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangling
 * @date 2021/11/22 5:46 下午
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public void hello() {
        System.out.println("hello world");
    }
}
