package com.bigdata.ecommerce.Service;

import com.bigdata.ecommerce.Models.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Product save(Product product);
    Iterable<Product> findAll();
    List<Product> findByProductId(String id);
    Set<String> getCategories();
    List<Product> getByCategory(String categoyName);
}
