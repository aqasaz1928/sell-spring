package com.imooc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by CZ on 2017/9/13.
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {


    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("[微信授权方法]..., code={}", code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx4b349a9cba0df9c2&secret=fd01380d6f7425553a063b03b6d69897&code="+
                code
                +"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String restResponse = restTemplate.getForObject(url, String.class);
        log.info("[response ={}]", restResponse);

    }

}
