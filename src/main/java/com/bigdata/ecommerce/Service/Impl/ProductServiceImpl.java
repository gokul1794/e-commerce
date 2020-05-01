package com.bigdata.ecommerce.Service.Impl;

import com.bigdata.ecommerce.Models.Category;
import com.bigdata.ecommerce.Models.Product;
import com.bigdata.ecommerce.Repository.ProductRepository;
import com.bigdata.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByProductId(String id) {
        return productRepository.findByProductId(id);
    }

    @Override
    public Set<String> getCategories() {
        Iterable<Product> products = productRepository.findAll();
        Set<String> categories = new HashSet<>();
        for(Product product : products){
            Category category = product.getCategory();
            categories.add(category.getCategoryName());
        }
        return categories;
    }

    @Override
    public List<Product> getByCategory(String categoryName) {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productsByCategory = new ArrayList<>();
        for (Product product : products){
            if(product.getCategory().getCategoryName().equals(categoryName)){
                productsByCategory.add(product);
            }
        }
        return productsByCategory;
    }
}
