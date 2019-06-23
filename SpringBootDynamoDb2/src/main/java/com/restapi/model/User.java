package com.restapi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@DynamoDBTable(tableName = "user")
@Data
public class User {

	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	@DynamoDBAttribute
	private String userId;

	@DynamoDBAttribute
	private String firstName;

	@DynamoDBAttribute
	private String lastName;

}