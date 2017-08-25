package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 查找所有在售商品
     * @return
     */
    List<ProductInfo> findAllOnSale();

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDTO> cartList);


    // 减库存
    void decreaseStock(List<CartDTO> cartList);

}
