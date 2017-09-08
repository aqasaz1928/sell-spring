package com.imooc.service.impl;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.enums.ProductionStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exceptions.SellException;
import com.imooc.repository.ProductInfoRepo;
import com.imooc.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

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
        return repo.findByProductStatus(ProductionStatusEnum.OnSale.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repo.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartList) {
        for (CartDTO cartDTO : cartList) {
            ProductInfo productInfo = repo.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                log.error("[增加库存] 未找到对应商品 CartDTO={}", cartDTO);
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if (cartDTO.getProductQuantity() < 0) {
                log.error("[获取商品数量] 订单商品数量为负 orderDTO={}", cartDTO);
                throw new SellException(ResultEnum.ORDER_QUANTITY_MINUS);
            }
            int stock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(stock);
            repo.save(productInfo);
        }

    }

    @Override
    public void increaseStock(List<OrderDetail> orderDetailList, int flag) {
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        increaseStock(cartDTOList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartList) {
        for (CartDTO cartDTO : cartList) {
            ProductInfo productInfo = repo.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERR);
            }
            productInfo.setProductStock(result);
            repo.save(productInfo);
        }

    }

    @Override
    public void decreaseStock(List<OrderDetail> orderDetailList, int flag) {
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        decreaseStock(cartDTOList);
    }


}
