package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductionStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl service;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = service.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = service.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
        Assert.assertEquals(1, productInfoPage.getTotalElements());
    }

    @Test
    public void findAllOnSale() throws Exception {
        List<ProductInfo> productInfoList = service.findAllOnSale();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo("654321", "测试商品", new BigDecimal(10.0), 100, "很好喝的粥", ProductionStatusEnum.OffSale.getStatus(),"https://123456.jpg",4);

        ProductInfo res = service.save(productInfo);
        Assert.assertEquals("654321", res.getProductId());
        Assert.assertEquals("测试商品", res.getProductName());
    }

}