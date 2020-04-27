package com.bigdata.ecommerce.Controller;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.bigdata.ecommerce.Models.Constants;
import com.bigdata.ecommerce.Models.Manufacturer;
import com.bigdata.ecommerce.Models.Product;
import com.bigdata.ecommerce.Repository.ProductRepository;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class API {

    @Autowired
    ProductRepository productService;

    /**
     * Gets All Products
     * @return
     */
    @GetMapping("/getProducts")
    public Iterable<Product> product() {
        Iterable<Product> products = productService.findAll();
        return products;
    }

    /**
     * Get's one product Detail
     * @param id
     * @return
     */
    @GetMapping("/getProductDetails")
    public List<Product> getProductDetails(@RequestParam("id") String id) {
        List<Product> product = productService.findByProductId(id);
        return product;
    }

    /**
     * Adds a product, maybe convert this to a list of products to add
     * @param
     * @return
     */
    @RequestMapping(value = "/addProducts", produces = {"application/json"}, method = RequestMethod.POST)
    public List<Product> createUser(@RequestBody List<Product> products) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", Constants.CLOUD_NAME,
                "api_key", Constants.API_KEY,
                "api_secret", Constants.API_SERCRET));
        List<Product> productsList = new ArrayList<>();
        try {
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                File file = new File(product.getUrl());
                Map uploadResult = cloudinary.uploader().upload(Files.readAllBytes
                        (new File(product.getUrl()).toPath()), ObjectUtils.emptyMap());
                products.get(i).setUrl(uploadResult.get("secure_url").toString());
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setModelNumber(product.getManufacturer().getModelNumber());
                manufacturer.setDate(new Date());
                products.get(i).setManufacturer(manufacturer);
                Product response = productService.save(product);
                ResponseEntity.status(HttpStatus.CREATED).body(response);
                productsList.add(response);
            }

        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productsList;
    }
}
