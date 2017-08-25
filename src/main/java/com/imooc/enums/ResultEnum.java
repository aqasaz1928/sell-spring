package com.imooc.enums;

import lombok.Getter;

/**
 * Created by CZ on 2017/8/25.
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERR(11, "商品库存不够")
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
