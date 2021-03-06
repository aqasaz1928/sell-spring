package com.imooc.controller;

import com.imooc.enums.ResultEnum;
import com.imooc.exceptions.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public void authorize(@RequestParam("returnUrl") String returnUrl,
                            HttpServletResponse httpServletResponse) throws IOException {

        String url = "http://yousa.nat100.top/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,
                WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        log.info("[微信网页授权] 获取 code rediresctUrl: {}", redirectUrl);

        httpServletResponse.sendRedirect(redirectUrl);
    }

    @GetMapping("/userInfo")
    public void userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl,
                           HttpServletResponse response) {

        log.info("[微信授权回调] 获得了code 和 state， code: {}, returnUrl: {}", code, returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            String openId = wxMpOAuth2AccessToken.getOpenId();
            log.info("[微信授权回调] 通过code获取 accessToken 并获得openId: {}", openId);
            response.sendRedirect(returnUrl + "?openid=" + openId);
        } catch (WxErrorException e) {
            log.error("[微信网页授权] 错误 error: {}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        } catch (IOException e) {
            log.error("[微信网页授权] 在进行http redirect的时候发生错误 errorMsg:{}", e);
            throw new SellException(ResultEnum.HTTP_REDIRECT_ERROR);
        }
    }

    @GetMapping("/auth")
    public String wechatAuth(@RequestParam("code") String code) {
        log.info("进入auth方法 code：{}", code);
        String openidRequestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+
                "wx4b349a9cba0df9c2"+
                "&secret="+
                "fd01380d6f7425553a063b03b6d69897"+
                "&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String resString = restTemplate.getForObject(openidRequestUrl, String.class);
        log.info("获得了openid接口返回， response：{}", resString);
        return null;
    }
}
