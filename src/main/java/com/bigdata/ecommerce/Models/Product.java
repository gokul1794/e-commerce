package com.bigdata.ecommerce.Models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "products")
public class Product {
    String productId;
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getProductId() {
        return productId;
    }
    @DynamoDBAttribute()
    String title;
    @DynamoDBAttribute()
    String description;
    @DynamoDBAttribute()
    String url;
    @DynamoDBAttribute()
    Manufacturer manufacturer;
    @DynamoDBAttribute()
    ShippingDetails shippingDetails;
    @DynamoDBAttribute()
    int quantity;
    @DynamoDBAttribute()
    Category category;
    @DynamoDBAttribute()
    Pricing pricing;


}
