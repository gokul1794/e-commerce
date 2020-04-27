package com.bigdata.ecommerce.Models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;

@Data
@DynamoDBDocument
public class Category {
    public String categoryName;
    public String color;
}
