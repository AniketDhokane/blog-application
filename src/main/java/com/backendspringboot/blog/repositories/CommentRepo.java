package com.backendspringboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendspringboot.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
	
	
}
