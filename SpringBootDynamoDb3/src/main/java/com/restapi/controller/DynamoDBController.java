package com.restapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.Customer;
import com.restapi.repository.DynamoDBRepository;

@RestController
@RequestMapping("/customer")
public class DynamoDBController {
	@Autowired
	private DynamoDBRepository dynamoDBRepository;

	@PostMapping("/add")
	public String addCustomer(@RequestBody Customer customer) {
		System.out.println("addCustomer method executed.");
		dynamoDBRepository.save(customer);
		return "Success";
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getOneCustomerDetails(@PathVariable String customerId) {
		Optional<Customer> customer = dynamoDBRepository.findById(customerId);
		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
	}

}
