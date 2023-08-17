package com.betsol.kanbanboardTask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betsol.kanbanboardTask.entity.Tbluser;
import com.betsol.kanbanboardTask.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<Tbluser> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/users/{userId}")
	public Tbluser getUserByUserId(@PathVariable("userId") String userId) {
		return userService.getUserById(userId);
	}
	
	@GetMapping("/login")
	public Tbluser login(@RequestParam("username") String username, @RequestParam("password") String password) {
		return userService.login(username, password);
	}
	
	@GetMapping("/adminLogin")
	public Tbluser adminLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		return userService.login(username, password);
	}
	
	@PostMapping("/register")
	public Tbluser register(@RequestBody Tbluser user) {
		return userService.register(user);
	}
	
	@GetMapping("/logout/{id}")
	public String logout(@PathVariable("id") Long id) {
//		return userService.logout(id);
		return "You have been logout";
	}
	
	@DeleteMapping("/softDeleteUser/{id}")
	public Tbluser softDeleteUser(@PathVariable("id") Long id) {
		return userService.softDeleteUser(id);
	}
	
	@PutMapping("/recoverUser/{id}")
	public Tbluser recoverUserById(@PathVariable("id") Long id) {
		return userService.recoverUserById(id);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public Tbluser deleteUser(@PathVariable("id") Long id) {
		return userService.deleteUser(id);
	}
	
}
