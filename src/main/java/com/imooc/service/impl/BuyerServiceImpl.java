package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exceptions.SellException;
import com.imooc.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by CZ on 2017/9/6.
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("[用户取消订单] 找不到对应订单 orderId: {}", orderId);
            throw new SellException(ResultEnum.FORM_PARAMS_ERR);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        // 验证订单是否为用户所有
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("[用户查询订单] 订单的openid与用户不一致 openid: {}, orderDTO: {}",openid, orderDTO);
            throw new SellException(ResultEnum.FORM_PARAMS_ERR);
        }
        return orderDTO;
    }

}
