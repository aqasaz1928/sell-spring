package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import com.imooc.repository.ProductCategoryRepo;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepo repo;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return  repo.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repo.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList) {
        return repo.findByCategoryTypeIn(typeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repo.save(productCategory);
    }
}
