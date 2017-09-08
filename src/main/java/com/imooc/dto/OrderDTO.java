package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.dataobject.OrderDetail;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by CZ on 2017/8/24.
 */
@Data
public class OrderDTO {

    private String orderId;              // 订单ID
    private String buyerName;            // 买家姓名
    private String buyerPhone;           // 买家电话
    private String buyerAddress;         // 买家地址
    private String buyerOpenid;          // 买家微信openid
    private BigDecimal orderAmount;      // 订单总金额

    // 订单状态  默认为新订单
    private Integer orderStatus;
    //  支付状态  默认为未支付
    private Integer payStatus;

    // 创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    // 更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
