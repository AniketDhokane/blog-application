package com.backendspringboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendspringboot.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	

}
