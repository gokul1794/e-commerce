package com.bigdata.ecommerce.Repository;

import com.bigdata.ecommerce.Models.Product;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findByProductId(String id);
}
