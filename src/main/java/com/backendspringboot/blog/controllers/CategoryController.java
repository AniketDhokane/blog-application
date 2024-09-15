package com.backendspringboot.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendspringboot.blog.payloads.ApiResponse;
import com.backendspringboot.blog.payloads.CategoryDTO;
import com.backendspringboot.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	
	@Autowired
	CategoryService categoryService;
	
	//create 
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
		//TODO: process POST request
		CategoryDTO createCatgory= this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(createCatgory,HttpStatus.CREATED);
	}
	
	
	
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Integer catId){
		CategoryDTO updatedCategory= this.categoryService.udpateCategory(categoryDTO, catId);
		return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
	}
	
	//delete
	
	@DeleteMapping("{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		
		 this.categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);
		
	}
	
	
	//get
	@GetMapping("{categoryId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId){
		
		CategoryDTO categoryDTO= this.categoryService.getCategoryById(categoryId);
		
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
		
	}
	
	
	//get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getCategories(){
		
		List<CategoryDTO> categoryDTOList= this.categoryService.getCategories();
		
		return new ResponseEntity<List<CategoryDTO>>(categoryDTOList, HttpStatus.OK);
		
	}
	

	
}
