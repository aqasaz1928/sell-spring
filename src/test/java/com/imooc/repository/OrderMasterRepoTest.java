package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by CZ on 2017/8/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepoTest {

    @Autowired
    private OrderMasterRepo repo;

    private final String OPENID = "o-hyhjjZB-SioYY3R21FndDLcqwo";

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest request = new PageRequest(0, 1);
        Page<OrderMaster> orderMasterPage = repo.findByBuyerOpenid(OPENID,  request);
        Assert.assertEquals(2, orderMasterPage.getTotalElements());
    }

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("order20170824");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setBuyerAddress("慕课网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(5.3));
        OrderMaster result = repo.save(orderMaster);
        Assert.assertEquals("order20170824", result.getOrderId());

    }


}