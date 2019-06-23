package com.restapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.User;
import com.restapi.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/add")
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@RequestMapping(value = "/list")
	public List<User> listUser() {
		return (List<User>) userRepository.findAll();
	}

	@RequestMapping("/findbyid")
	public User findById(@PathVariable("id") String id) {
		Optional<User> op = userRepository.findById(id);
		return op.get();
	}

	@RequestMapping("/findbylastname")
	public List<User> fetchDataByLastName(@RequestParam("lastname") String lastName) {
		return userRepository.findByLastName(lastName);
	}

	@RequestMapping("/delete")
	public String delete() {
		userRepository.deleteAll();
		return "Done";
	}
}
