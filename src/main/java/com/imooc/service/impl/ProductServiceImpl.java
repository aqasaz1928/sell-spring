package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.enums.ProductionStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exceptions.SellException;
import com.imooc.repository.ProductInfoRepo;
import com.imooc.service.ProductService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartList) {

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartList) {
        for (CartDTO cartDTO: cartList) {
            ProductInfo productInfo = repo.findOne(cartDTO.getProductId());
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERR);
            }
            productInfo.setProductStock(result);
            repo.save(productInfo);
        }

    }
}
