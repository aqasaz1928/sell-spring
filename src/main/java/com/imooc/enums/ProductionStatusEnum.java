package com.imooc.enums;


import lombok.Getter;

/**
 * 商品状态枚举
 */
@Getter
public enum ProductionStatusEnum {
    OnSale(0, "商品在售"),
    OffSale(1, "商品下架")
    ;

    private Integer code;
    private String message;
    ProductionStatusEnum(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
