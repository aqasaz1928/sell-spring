package com.imooc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by CZ on 2017/9/8.
 */
@RestController
@RequestMapping("/token")
public class WxTestController {

    @GetMapping("/get")
    public String getToken(){
        return "selltesttoken";
    }
}
