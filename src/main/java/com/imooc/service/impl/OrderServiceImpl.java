package com.imooc.service.impl;

import com.imooc.converter.OrderMaster2OrderDTOConver;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exceptions.SellException;
import com.imooc.repository.OrderDetailRepo;
import com.imooc.repository.OrderMasterRepo;
import com.imooc.service.OrderService;
import com.imooc.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by CZ on 2017/8/24.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepo masterRepo;
    @Autowired
    OrderDetailRepo detailRepo;
    @Autowired
    ProductServiceImpl productService;

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
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.createUniqueKey());
            BeanUtils.copyProperties(productInfo, orderDetail);
            detailRepo.save(orderDetail);
        }
        OrderMaster orderMaster = new OrderMaster();
//        orderMaster.setOrderId(orderId);
//        orderMaster.setOrderAmount(orderAmount);
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());
        masterRepo.save(orderMaster);
        // 商品扣库存
        productService.decreaseStock(orderDTO.getOrderDetailList(), 0);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = masterRepo.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = detailRepo.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.DETAIL_LIST_NOT_EXIST);
        }
        OrderDTO result = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, result);
        result.setOrderDetailList(orderDetailList);
        return result;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = masterRepo.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConver.convert(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());

    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        // 查询订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("该订单状态不能取消， id:{}, status: {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERR);
        }
        // 关闭订单
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster orderMasterSaveRes = masterRepo.save(orderMaster);
        if (orderMasterSaveRes == null) {
            log.error("[取消订单] 更新失败，OrderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 返还库存
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.warn("[取消订单] 订单中无商品 orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        productService.increaseStock(orderDetailList, 0);

        // 已支付 退款
        if (orderDTO.getPayStatus() == PayStatusEnum.PAID.getCode()) {
            //TODO
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        // 查找订单 判断状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[完结订单操作] 订单状态不正确 orderid:{}, orderstatus:{}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERR);
        }
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        // 修改订单状态
        OrderMaster updateResult = masterRepo.save(orderMaster);
        if (updateResult==null){
            log.error("[结束订单操作] 数据跟新失败，orderMaster: {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO pay(OrderDTO orderDTO) {
        // 查找订单 判断状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[支付订单操作] 订单状态不正确 orderid:{}, orderstatus:{}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERR);
        }
        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.UNPAID.getCode())) {
            log.error("[订单支付操作] 订单支付状态错误, orderid:{}, paystatus:{} ", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERR);
        }
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.PAID.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = masterRepo.save(orderMaster);
        if (updateResult==null){
            log.error("[更新支付状态] 数据跟新失败，orderMaster: {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
