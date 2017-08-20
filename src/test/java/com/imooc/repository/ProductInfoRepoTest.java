package com.imooc.repository;

import com.imooc.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepoTest {

    @Autowired
    private ProductInfoRepo repo;
    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> resList = repo.findByProductStatus(0);
        Assert.assertNotEquals(0, resList.size());
    }

    @Test
    public void findByAndCategoryType() throws Exception {
        List<ProductInfo> resList = repo.findByAndCategoryType(4);
        Assert.assertNotEquals(0, resList.size());
    }

    @Test
    public void  saveTest() throws  Exception {
        ProductInfo productInfo = new ProductInfo("123456", "皮蛋粥", new BigDecimal(10.0), 100, "很好喝的粥",0,"https://123456.jpg",4);
        ProductInfo res =  repo.save(productInfo);
        System.out.println(res.toString());
        Assert.assertNotEquals(null, res);
    }

}