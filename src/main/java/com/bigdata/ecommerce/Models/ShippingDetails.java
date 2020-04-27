package com.bigdata.ecommerce.Models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;

@Data
@DynamoDBDocument
public class ShippingDetails {
    float weight;
    float width;
    float height;
    float depth;

}
