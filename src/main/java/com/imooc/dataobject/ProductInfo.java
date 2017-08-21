package com.imooc.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;                           // 商品ID

    private String productName;                 // 商品名称
    private BigDecimal productPrice;         // 商品价格
    private Integer productStock;               // 商品库存
    private String productDescription;      // 商品描述
    private Integer productStatus;             // 商品状态 0 正常 1下架
    private String productIcon;                   // 商品图片
    private  Integer categoryType;              // 类目编号

    public ProductInfo() {
    }

    /**
     *
     * @param productId String
     * @param productName String
     * @param productPrice BigDecimal
     * @param productStock Integer
     * @param productDescription String
     * @param productStatus Integer
     * @param productIcon Integer
     * @param categoryType Integer
     */
    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDescription, Integer productStatus, String productIcon, Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productStatus = productStatus;
        this.productIcon = productIcon;
        this.categoryType = categoryType;
    }
}
