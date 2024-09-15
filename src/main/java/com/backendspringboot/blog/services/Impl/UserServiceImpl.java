package com.backendspringboot.blog.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendspringboot.blog.config.AppConstants;
import com.backendspringboot.blog.entities.Role;
import com.backendspringboot.blog.entities.User;
import com.backendspringboot.blog.exceptions.ResourceNotFoundException;
import com.backendspringboot.blog.payloads.UserDTO;
import com.backendspringboot.blog.repositories.RoleRepo;
import com.backendspringboot.blog.repositories.UserRepo;
import com.backendspringboot.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private RoleRepo roleRepo; 
	 
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user =this.dtoToUser(userDTO);
		User savedUser= this.userRepo.save(user);
		
		return this.userToDto(savedUser);
		
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		
		User updatedUser= userRepo.save(user);
		
		UserDTO userDto1= userToDto(updatedUser);
		
		return userDto1;
		
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user= this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		UserDTO userDTO= userToDto(user);
				
		return userDTO;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		
		List<User> users= this.userRepo.findAll();
		
		/*
		List<UserDTO> userDTOs=new ArrayList<>();
		for(User u: user) {
			
			userDTOs.add(userToDto(u));
			
		}*/
		List<UserDTO> userDTOs= users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		
		
		return userDTOs;
		}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		this.userRepo.delete(user);
		
		
	}
	
	public  User dtoToUser(UserDTO userDTO) {
		User user= this.modelMapper.map(userDTO,User.class);
		
		//user.setId(userDTO.getId());
		//user.setName(userDTO.getName());
		//user.setEmail(userDTO.getEmail());
		//user.setAbout(userDTO.getAbout());
		//user.setPassword(userDTO.getPassword());
		
		return user;
	}

	public UserDTO userToDto(User user)
	{
		UserDTO userDTO= this.modelMapper.map(user, UserDTO.class);
		
		//userDTO.setId(user.getId());
		//userDTO.setName(user.getName());
		//userDTO.setEmail(user.getEmail());
		//userDTO.setAbout(user.getAbout());
		//userDTO.setPassword(user.getPassword());
		
		return userDTO;
		
		
	}
	
	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {
		
		
		User user= this.modelMapper.map(userDTO, User.class);
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser=	this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDTO.class);
		
	}
	


}
