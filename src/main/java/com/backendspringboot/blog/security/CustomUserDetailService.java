package com.backendspringboot.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backendspringboot.blog.entities.User;
import com.backendspringboot.blog.repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	 	@Autowired
	    private UserRepo userRepository;

	    @Override
	    @Transactional
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    	 // Fetch user by email (username)
	        User user = userRepository.findByEmail(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	
	        // Returning the user if User implements UserDetails
	        // Otherwise, map the necessary fields to create a Spring Security UserDetails object
	        return org.springframework.security.core.userdetails.User
	                .withUsername(user.getEmail())  // Assuming user.getEmail() returns the username/email
	                .password(user.getPassword())
	                .authorities(user.getAuthorities()) // Ensure this returns a collection of GrantedAuthority
	                .accountExpired(false)
	                .accountLocked(false)
	                .credentialsExpired(false)
	                .disabled(false)
	                .build();
	    }

}
