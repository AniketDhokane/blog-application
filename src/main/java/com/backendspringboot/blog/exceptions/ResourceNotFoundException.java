package com.backendspringboot.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	String resourceName;
	String fieldNameString;
	long fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldNameString, long fieldValue) {
		super(String.format("%s not found with %s : %s",resourceName,fieldNameString,fieldValue));
		this.resourceName = resourceName;
		this.fieldNameString = fieldNameString;
		this.fieldValue = fieldValue;
	}
	
	 

}
