package com.imooc.service.impl;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.OrderDTO;
import com.imooc.repository.OrderDetailRepo;
import com.imooc.repository.OrderMasterRepo;
import com.imooc.service.OrderService;
import com.imooc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CZ on 2017/8/24.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepo masterRepo;
    @Autowired
    OrderDetailRepo detailRepo;
    @Autowired
    ProductService productService;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        /**
         *  1、查询商品状态，数量和价格
         *  2、计算总价
         *  3、写入订单数据库
         *  4、减库存
         */

        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());

        }
        return null;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        return null;
    }
}
