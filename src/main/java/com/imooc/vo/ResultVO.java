package com.imooc.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 前端请求返回的最外层对象
 * Created by CZ on 2017/8/21.
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T>{

    // 错误码
    private Integer code;
    // 错误信息
    private String msg;

    //具体内容
    private T data;
}
