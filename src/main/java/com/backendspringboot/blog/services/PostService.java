package com.backendspringboot.blog.services;

import java.util.List;

import com.backendspringboot.blog.payloads.PostDTO;
import com.backendspringboot.blog.payloads.PostResponse;



public interface PostService {
	
	
	
	//create 
	
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
	
	//update
	
	public PostDTO updatePost(PostDTO postDTO, Integer postId);
	
	//delete
	
	public void deletePost(Integer postId);
	
	//get all post
	
	public PostResponse getAllPost( Integer pageNumber, Integer pageSize,String sortBy, String sortDir);
	
	//get post by id
	public PostDTO getPostById(Integer postId);
	
	//get all post by category
	public List<PostDTO> getPostsByCategory(Integer categoryId);
	
	//get all post by user
	public List<PostDTO> getPostsByUsers(Integer userId);
	
	//search post 
	
	public List<PostDTO> searchPost(String keyword);
	
	

}
