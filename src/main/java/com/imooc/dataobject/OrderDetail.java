package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Created by CZ on 2017/8/23.
 */
@Data
@Entity
@DynamicUpdate
public class OrderDetail {

    @javax.persistence.Id
    private String detailId;                 // 订单详情 id
    private String orderId;                 //  订单 id
    private String productId;             //  商品id
    private String productName;       //  商品名称
    private String productIcon;          //  商品标签
    //商品价格 单位分
    private BigDecimal productPrice;
    // 商品购买数量
    private Integer productQuantity;

    public OrderDetail() {
    }
}
