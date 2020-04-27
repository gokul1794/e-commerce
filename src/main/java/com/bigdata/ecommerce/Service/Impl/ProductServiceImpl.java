package com.bigdata.ecommerce.Service.Impl;

import com.bigdata.ecommerce.Models.Product;
import com.bigdata.ecommerce.Repository.ProductRepository;
import com.bigdata.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
