package com.restapi.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.restapi.model.Customer;




@EnableScan
public interface DynamoDBRepository extends CrudRepository<Customer, String> {
    
}