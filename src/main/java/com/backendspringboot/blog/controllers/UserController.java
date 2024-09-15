	package com.backendspringboot.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendspringboot.blog.payloads.ApiResponse;
import com.backendspringboot.blog.payloads.UserDTO;
import com.backendspringboot.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService; 
	
	
	//Post method for create users
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		
	UserDTO createdUserDto=	userService.createUser(userDTO);
		
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	//Put method for update users
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Integer uid)
	{
		UserDTO updatedUserDTO= userService.updateUser(userDTO,uid);
		
		return new ResponseEntity<>(updatedUserDTO,HttpStatus.OK);
	}	
	//Delete method for delete users
	//Admin can do delete only 
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
	
	//Get method for fetch users
	//get all users 
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		
		return ResponseEntity.ok(this.userService.getAllUsers());
		 
	}
	
	//get user by id 
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer uid){
		
		return  ResponseEntity.ok(userService.getUserById(uid));
	}
	
	

}
