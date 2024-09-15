package com.backendspringboot.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

//	
	private int id;
	

	@Size(min=4, message = "Username must be min of 4 charaters") 
	@NotEmpty
	private String name;
	
	@Email(message = "Email is not valid !")
	@Column(unique = true)
	@NotEmpty(message = "Email is not valid !")
	private String email;
	
	@NotEmpty
	@Size(min=3, max = 10, message = "Password must be min of 3 chars and max of 10 chars !!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDTO> roles= new HashSet<>();
	
}
