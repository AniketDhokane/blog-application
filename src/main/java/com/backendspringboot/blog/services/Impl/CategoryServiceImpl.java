package com.backendspringboot.blog.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendspringboot.blog.entities.Category;
import com.backendspringboot.blog.exceptions.ResourceNotFoundException;
import com.backendspringboot.blog.payloads.CategoryDTO;
import com.backendspringboot.blog.repositories.CategoryRepo;
import com.backendspringboot.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		
		Category cat = this.modelMapper.map(categoryDTO, Category.class);
		Category addedCategory= this.categoryRepo.save(cat);
		
		return this.modelMapper.map(addedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO udpateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryDescription(categoryDTO.getCategoryDescription());
		
		Category updatedCategory= this.categoryRepo.save(category);
		
		
		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
		// TODO Auto-generated method 

		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		
		return this.modelMapper.map(category, CategoryDTO.class); 
	}

	@Override
	public List<CategoryDTO> getCategories() {
		// TODO Auto-generated method stub
		
		List<Category>  categories= this.categoryRepo.findAll();
		
		List<CategoryDTO> categoryDTOs= categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDTOs;
	}

}
