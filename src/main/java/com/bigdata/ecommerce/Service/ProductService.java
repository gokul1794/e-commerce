package com.bigdata.ecommerce.Service;

import com.bigdata.ecommerce.Models.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Product save(Product product);
}
