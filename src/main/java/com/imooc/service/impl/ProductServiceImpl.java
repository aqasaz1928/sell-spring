package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductionStatusEnum;
import com.imooc.repository.ProductInfoRepo;
import com.imooc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl  implements ProductService {

    @Autowired
    private ProductInfoRepo repo;
    @Override
    public ProductInfo findOne(String productId) {
        return repo.findOne(productId);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findAllOnSale() {
        return repo.findByProductStatus(ProductionStatusEnum.OnSale.getStatus());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repo.save(productInfo);
    }
}
