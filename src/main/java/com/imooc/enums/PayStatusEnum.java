package com.imooc.enums;

import lombok.Getter;

/**
 * Created by CZ on 2017/8/23.
 */
@Getter
public enum PayStatusEnum {
    UNPAID(0, "未支付"),
    PAID(1, "已支付"),
    REFUND(2, "以退款")
    ;

    private Integer code;
    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
