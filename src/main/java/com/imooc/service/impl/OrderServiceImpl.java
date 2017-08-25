package com.imooc.service.impl;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exceptions.SellException;
import com.imooc.repository.OrderDetailRepo;
import com.imooc.repository.OrderMasterRepo;
import com.imooc.service.OrderService;
import com.imooc.service.ProductService;
import com.imooc.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        /**
         *  1、查询商品状态，数量和价格
         *  2、计算总价
         *  3、写入订单数据库
         *  4、减库存
         */
        String orderId = KeyUtil.createUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            // 订单入库
            orderDetail.setOrderId(KeyUtil.createUniqueKey());
            orderDetail.setDetailId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            detailRepo.save(orderDetail);
        }
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        masterRepo.save(orderMaster);
        // 商品扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
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
