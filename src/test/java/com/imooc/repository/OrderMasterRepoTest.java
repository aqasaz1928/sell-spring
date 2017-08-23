package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Test
    public void findByBuyerOpenid() throws Exception {
    }

    @Test
    public void saveTets() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("order20170823");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setBuyerAddress("慕课网");
        orderMaster.setBuyerOpenid("o-hyhjjZB-SioYY3R21FndDLcqwo");
        orderMaster.setOrderAmount(new BigDecimal(2.3));
        OrderMaster result = repo.save(orderMaster);
        Assert.assertEquals("order20170823", result.getOrderId());

    }


}