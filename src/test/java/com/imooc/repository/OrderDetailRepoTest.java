package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by CZ on 2017/8/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepoTest {

    @Autowired
    private OrderDetailRepo repo;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567891");
        orderDetail.setOrderId("1111112");
        orderDetail.setProductId("111112");
        orderDetail.setProductName("红烧肉");
        orderDetail.setProductIcon("http://hongshaorou.jpg");
        orderDetail.setProductPrice(new BigDecimal(15.2));
        orderDetail.setProductQuantity(3);
        OrderDetail result = repo.save(orderDetail);
        Assert.assertEquals("1234567890", result.getDetailId());

    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetailList = repo.findByOrderId("1111112");
        Assert.assertEquals("1234567891", orderDetailList.get(0).getDetailId());
    }

}