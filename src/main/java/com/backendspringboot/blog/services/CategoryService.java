package com.backendspringboot.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backendspringboot.blog.payloads.CategoryDTO;

@Service
public interface CategoryService {
	
	//create
	
	public CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	//update 

	public CategoryDTO udpateCategory(CategoryDTO categoryDTO, Integer categoryId);

	//delete
	
	public void deleteCategory(Integer categoryId);
	
	
	//get
	
	public CategoryDTO getCategoryById(Integer categoryId);
	
	//get all 
	
	List<CategoryDTO> getCategories();

}
