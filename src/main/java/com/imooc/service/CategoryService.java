package com.imooc.service;


import com.imooc.dataobject.ProductCategory;
import org.apache.tomcat.jni.Procattr;

import java.util.List;

/**
 * 商品类目 service
 */
public interface CategoryService  {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);

    ProductCategory save(ProductCategory productCategory);


}
