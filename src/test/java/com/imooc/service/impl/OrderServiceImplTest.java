package com.imooc.service.impl;

import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dto.OrderDTO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CZ on 2017/8/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl service;
    private final String BUYER_OPENID = "1101110";
    private final String TEST_ORDER_ID = "1504150212084515361";

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("慕课网");
        orderDTO.setBuyerName("廖师兄");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        // 购物车列表
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail od1 = new OrderDetail();
        od1.setProductId("223415");
        od1.setProductQuantity(1);

//        OrderDetail od2 = new OrderDetail();
//        od2.setProductId("654321");
//        od2.setProductQuantity(2);

        orderDetailList.add(od1);
//        orderDetailList.add(od2);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = service.create(orderDTO);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO result = service.findOne(TEST_ORDER_ID);
        log.info("查询订单测试 {}", result);
        Assert.assertEquals(TEST_ORDER_ID, result.getOrderId());
    }

    @Test
    public void findList() throws Exception {
        Page<OrderDTO> orderDTOPage = service.findList(BUYER_OPENID, new PageRequest(0,10));
        log.info("find order number: {}",orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = service.findOne(TEST_ORDER_ID);
        OrderDTO result = service.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = service.findOne(TEST_ORDER_ID);
        OrderDTO result = service.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(), result.getOrderStatus());
    }

    @Test
    public void pay() throws Exception {
        OrderDTO orderDTO = service.findOne(TEST_ORDER_ID);
        OrderDTO result = service.pay(orderDTO);
        Assert.assertEquals(PayStatusEnum.PAID.getCode(), result.getPayStatus());
    }

}