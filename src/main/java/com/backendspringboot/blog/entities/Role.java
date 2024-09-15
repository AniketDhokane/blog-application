package com.backendspringboot.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

	
	
	@Id
	private int id;
	
	private String name;
}
