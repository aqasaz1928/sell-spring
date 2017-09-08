package com.imooc.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by CZ on 2017/9/4.
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /**
     * 买家地址
     */
    @NotEmpty(message = "地址不能为空")
    private String address;

    /**
     * 买家电话
     */
    @NotEmpty(message = "手机号不能为空")
    private String phone;


    /**
     * 买家openid
     */
    @NotEmpty(message = "openid不能为空")
    private String openid;

    /**
     * 购物车信息
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;

    public OrderForm() {
    }
}
