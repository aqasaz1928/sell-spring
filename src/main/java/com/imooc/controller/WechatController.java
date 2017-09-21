package com.imooc.controller;

import com.imooc.enums.ResultEnum;
import com.imooc.exceptions.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * Created by CZ on 2017/9/14.
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatController {


    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

        String url = "http://yousa.nat100.top/sell/wechat/userInfo";
        String rediresctUrl = wxMpService.oauth2buildAuthorizationUrl(url,
                WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        log.info("[微信网页授权] 获取 code rediresctUrl: {}", rediresctUrl);

        return "redirect:" + rediresctUrl;


    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {

        log.info("[微信授权回调] 获得了code 和 state， code: {}, returnUrl: {}", code, returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            String openId = wxMpOAuth2AccessToken.getOpenId();
            log.info("[微信授权回调] 通过code获取 accessToken 并获得openId: {}", openId);
            return "redirect:" + returnUrl + "?openid=" + openId;
        } catch (WxErrorException e) {
            log.error("[微信网页授权] 错误 error: {}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
    }
}
