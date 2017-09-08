package com.imooc.enums;

import lombok.Getter;

/**
 * Created by CZ on 2017/8/25.
 */
@Getter
public enum ResultEnum {
    FORM_PARAMS_ERR(1, "表单参数错误"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERR(11, "商品库存不够"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    DETAIL_LIST_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERR(14, "订单状态错误"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    ORDER_DETAIL_EMPTY(16, "订单中没有商品"),
    ORDER_QUANTITY_MINUS(17,"商品数量不能为负"),
    ORDER_PAY_STATUS_ERR(18,"订单支付状态不正确"),
    EMPTY_CART(19, "购物车为空")
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
