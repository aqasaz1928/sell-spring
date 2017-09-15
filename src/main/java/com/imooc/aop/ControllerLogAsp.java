package com.imooc.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by CZ on 2017/9/11.
 */
@Aspect
@Configuration
@Slf4j
public class ControllerLogAsp {

//    @Pointcut("execution(* com.imooc.controller.WxTestController.*(..))")
//    public void execute() {
//
//    }
//
//    @Around("execute()")
//    public void executionLog() {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//    }

}
