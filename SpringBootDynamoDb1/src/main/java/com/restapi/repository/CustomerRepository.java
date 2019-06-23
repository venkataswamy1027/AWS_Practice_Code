package com.restapi.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.restapi.model.Customer;

@Repository
public class CustomerRepository {
	@Autowired
	private DynamoDBMapper mapper;

	public void insertIntoDynamoDB(Customer customer) {
		mapper.save(customer);
	}

	public Customer getCustomerDetails(String customerId) {
		return mapper.load(Customer.class, customerId);
	}

	public void updateCustomerDetails(Customer customer) {
		try {
			mapper.save(customer, buildDynamoDBSaveExpression(customer));

		} catch (ConditionalCheckFailedException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private DynamoDBSaveExpression buildDynamoDBSaveExpression(Customer customer) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("customerId", new ExpectedAttributeValue(new AttributeValue(customer.getCustomerId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
	}

	public void deleteCustomerDetails(Customer customer) {
		mapper.delete(customer);
	}

	public List<Customer> findAll() {
		return mapper.scan(Customer.class, new DynamoDBScanExpression());
	}

	public Customer searchCustomer(String lastName) {
		return mapper.load(Customer.class, lastName);
	}
}
