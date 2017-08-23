package com.imooc.enums;

import lombok.Getter;

/**
 * Created by CZ on 2017/8/23.
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    FINISH(1, "已完成"),
    CANCEL(2, "已取消")
    ;
    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
