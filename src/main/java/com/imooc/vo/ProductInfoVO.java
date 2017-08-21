package com.imooc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 * Created by CZ on 2017/8/21.
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("icon")
    private String productIcon;
    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("price")
    private BigDecimal productPrice;
}
