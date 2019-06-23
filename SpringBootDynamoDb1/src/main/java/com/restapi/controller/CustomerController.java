package com.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.Customer;
import com.restapi.repository.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	@PostMapping("/add")
	public String addCustomer(@RequestBody Customer customer) {
		System.out.println("addCustomer method executed.");
		customerRepository.insertIntoDynamoDB(customer);
		return "Successfully inserted Into the Table";
	}

	@RequestMapping(value = "/list")
	public List<Customer> listCustomers() {
		System.out.println("listCustomers method executed.");
		return customerRepository.findAll();
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getOneCustomerDetails(@PathVariable String customerId) {
		System.out.println("getOneCustomerDetails method executed.");
		Customer customer = customerRepository.getCustomerDetails(customerId);
		System.out.println(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	// custom search not working
	@GetMapping
	public ResponseEntity<Customer> searchCustomer(@RequestParam String lastName) {
		System.out.println("searchCustomerByLasName method executed.");
		System.out.println("lastName " + lastName);
		Customer customer = customerRepository.searchCustomer(lastName);
		if (customer == null) {
			return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PutMapping
	public String updateCustomerDetails(@RequestBody Customer customer) {
		System.out.println("updateCustomerDetails method executed.");
		customerRepository.updateCustomerDetails(customer);
		return "Record updated Successfully.";
	}

	@DeleteMapping(value = "/{customerId}")
	public String deleteCustomerDetails(@PathVariable("customerId") String customerId) {
		System.out.println("deleteCustomerDetails method executed.");
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		customerRepository.deleteCustomerDetails(customer);
		return "Record deleted Successfully.";
	}
}
