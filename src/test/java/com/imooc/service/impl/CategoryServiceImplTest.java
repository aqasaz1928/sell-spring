package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl service;

    @Test
    public void findOneTest() throws Exception {
        ProductCategory productCategory = service.findOne(2);
        Assert.assertEquals(new Integer(2), productCategory.getCategoryId());
    }
    @Test
    public void findAllTest() throws Exception {
        List<ProductCategory> productCategories = service.findAll();
        Assert.assertNotEquals(0, productCategories.size());

    }
    @Test
    public void findListTest() throws Exception {
        List<Integer> typeList = java.util.Arrays.asList(2,3);
        List<ProductCategory> productCategories = service.findByCategoryTypeIn(typeList);
        Assert.assertEquals(new Long(1),  new Long(productCategories.size()));
    }
    @Test
    @Transactional
    public void saveTest() throws Exception {
        ProductCategory productCategory = new ProductCategory("测试类目", 99);
        ProductCategory res = service.save(productCategory);
        Assert.assertEquals(new Long(99), new Long(res.getCategoryType()));

    }
}