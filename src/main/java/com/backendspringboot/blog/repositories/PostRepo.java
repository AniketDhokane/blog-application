package com.backendspringboot.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backendspringboot.blog.entities.Category;
import com.backendspringboot.blog.entities.Post;
import com.backendspringboot.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findAllByUser(User user);
	
	List<Post> findAllByCategory(Category category);

	@Query("select p from Post p where p.title like :key")
	List<Post> findByTitle(@Param("key") String title);

}
