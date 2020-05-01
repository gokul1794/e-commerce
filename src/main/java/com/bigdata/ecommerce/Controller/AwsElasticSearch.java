package com.bigdata.ecommerce.Controller;

import com.bigdata.ecommerce.Models.Category;
import com.bigdata.ecommerce.Models.Constants;
import com.bigdata.ecommerce.Models.Product;
import com.bigdata.ecommerce.Repository.ProductRepository;
import com.bigdata.ecommerce.Service.ProductService;
import com.bigdata.ecommerce.Service.StorageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
public class AwsElasticSearch {

    @Value("${elasticsearch.host}")
    private String awsEndpoint;

    @Autowired
    ProductService productService;

    @GetMapping("/indexProducts")
    public String IndexData() throws IOException {

        /**
         * Let's fetch the file from DynamoDb and Index it in aws Elasticsearch
         */
        Iterable<Product> products = productService.findAll();
        int i = 0;
        for(Product product: products){
            JSONObject productJson = new JSONObject(product);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSON, productJson.toString());

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(awsEndpoint+"/products/_doc/"+i+1)
                    .method("PUT", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            i++;
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        }
            return "Successfully Indexed?";
    }

    @GetMapping("/indexCategories")
    public String IndexCategories() throws IOException {

        /**
         * Let's fetch the file from DynamoDb and Index it in aws Elasticsearch
         */
        Set<String> categories = productService.getCategories();
        Set<String> categorySet = new HashSet<>();
        for(String category: categories){
            categorySet.add(category);
        }
        int j = 0;
        for(String category : categorySet){

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            com.squareup.okhttp.RequestBody body = RequestBody.create(JSON, category);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(awsEndpoint+"/categories/_doc/"+j+1)
                    .method("PUT", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            j++;
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        }
        return "Successfully Indexed?";
    }

    @GetMapping("/search")
    public String SearchIndex(@RequestParam String q) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(awsEndpoint+"/products/_search?q="+q)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
