package com.bigdata.ecommerce.Models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;

import java.util.Date;

@Data
@DynamoDBDocument
public class Manufacturer {
    String ModelNumber;
    Date date;
}
