package com.smartimpulse.trainapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smartimpulse.trainapi.model.User;
import com.smartimpulse.trainapi.repository.UserRepository;

@RestController
@RequestMapping("users/")
public class UserController {
	@Autowired
	private UserRepository repository;
	
	@GetMapping
	public List<User> GetUsers() {
		return repository.findAll();
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping
	public User AddUser(@RequestBody User user) throws Exception {
		Optional<User> oldUser = repository.findByUsername(user.getUsername());
		
		if(oldUser.isPresent()) {
			throw new Exception(
			          "There is an account with that username:" + user.getUsername());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.insert(user);
	}
	
	@PutMapping
	public User UpdateUser(@RequestBody User user) {
		User oldUser = repository.findByUsername(user.getUsername()).orElseThrow();
		user.set_id(oldUser.get_id());;
		user.setPassword(oldUser.getPassword());
		return repository.save(user);
	}
	
	@PostMapping("changePassword/")
	public void UpdatePassword(@RequestBody Map<String, String> body) throws Exception {
		Optional<User> oldUser = repository.findByUsername(body.get("username"));
		if(oldUser.isEmpty()) {
			throw new Exception("Username Not Found");
		}
		
		String newPassword = passwordEncoder.encode(body.get("newPassword"));
		String oldPassword = passwordEncoder.encode(body.get("oldPassword"));
		
		if(oldPassword == oldUser.get().getPassword()) {
			User user = oldUser.get();
			user.setPassword(newPassword);
			repository.save(user);
			return;
		}
		throw new Exception("old Password did not match");
	}
	
	@GetMapping("{username}/")
	public Optional<User> GetUser(@PathVariable String username) {
		return repository.findByUsername(username);
	}
	
	@DeleteMapping("{username}/")
	public void DeleteBooking(@PathVariable String username) {
		repository.deleteByUsername(username);
	}
}
