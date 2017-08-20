package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
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

    // 减库存
}
