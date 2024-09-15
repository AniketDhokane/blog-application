package com.backendspringboot.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendspringboot.blog.payloads.JwtAuthRequest;
import com.backendspringboot.blog.payloads.JwtAuthResponse;
import com.backendspringboot.blog.payloads.UserDTO;
import com.backendspringboot.blog.repositories.UserRepo;
import com.backendspringboot.blog.security.JwtHelper;
import com.backendspringboot.blog.services.UserService;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	private static final Logger logger= LoggerFactory.getLogger(AuthController.class);

	    @Autowired
	    private AuthenticationManager authenticationManager;
	    
	    @Autowired
	    private JwtHelper jwtHelper;
	    
	    @Autowired
	    private UserDetailsService userDetailsService;
	    
	    @Autowired
	    private UserRepo userRepo;
	    
	    @Autowired
	    UserService userService;
	    
		 @Autowired
		 private BCryptPasswordEncoder passwordEncoder;
	   
	    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtHelper jwtHelper, UserRepo userRepositoy) {
	        this.authenticationManager = authenticationManager;
	        this.userDetailsService = userDetailsService;
	        this.jwtHelper = jwtHelper;
	        this.userRepo = userRepo;
	    }


	    @PostMapping("/login")
	    public ResponseEntity<?> authenticate(@RequestBody JwtAuthRequest request) throws Exception{
	    	
	    	try {
	            // Authenticate the user
	            Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
	            
	            // Set the authentication in the security context
	            SecurityContextHolder.getContext().setAuthentication(authentication);

	            // Load user details
	            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

	            // Generate JWT token
	            final String jwt = jwtHelper.generateToken(userDetails);

	            // Return the JWT and username
	            return new ResponseEntity<>(new JwtAuthResponse(jwt, userDetails.getUsername()), HttpStatus.OK);

	        } catch (BadCredentialsException e) {
	            logger.error("Invalid credentials for user: {}", request.getUsername());
	            throw new Exception("Incorrect username or password", e);
	        }
	    }
	    @PostMapping("/register")
	    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
	       
	    	UserDTO registeredUser = this.userService.registerNewUser(userDTO);
	    	
	        
			return new ResponseEntity<UserDTO>(registeredUser,HttpStatus.CREATED);
	    }
	   
	    
	    private void doAuthenticate(String username, String password) {

	       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

	       this.authenticationManager.authenticate(authenticationToken);
	       logger.info("User {} authenticated successfully", username);
	     
	    }
	     
	    
	    @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<String> handleBadCredentialsException() {
	        return new ResponseEntity<>("Invalid credentials, please check your username and password", HttpStatus.BAD_REQUEST);
	    }
	 
	  

}   
