package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepoTest {

    @Autowired
    private ProductCategoryRepo repo;

    @Before
    public void setUp() throws Exception {
        log.info("before test");
    }

    @After
    public void tearDown() throws Exception {
        log.info("after test");
    }

    @Test
    public void test() {
        ProductCategory productCategory = repo.findOne(1);
        log.warn("res: {}",productCategory.toString());
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("女生最爱", 4);
        ProductCategory res = repo.save(productCategory);
        Assert.assertNotNull(res);

    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> typeList = Arrays.asList(2,3,4);
        List<ProductCategory> resList = repo.findByCategoryTypeIn(typeList);
        Assert.assertNotEquals(0, resList.size());

    }

}