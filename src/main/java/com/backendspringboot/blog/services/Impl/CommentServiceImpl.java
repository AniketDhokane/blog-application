package com.backendspringboot.blog.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendspringboot.blog.entities.Comment;
import com.backendspringboot.blog.entities.Post;
import com.backendspringboot.blog.exceptions.ResourceNotFoundException;
import com.backendspringboot.blog.payloads.CommentDTO;
import com.backendspringboot.blog.repositories.CommentRepo;
import com.backendspringboot.blog.repositories.PostRepo;
import com.backendspringboot.blog.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO creatComment(CommentDTO commentDTO, Integer postId) {
		// TODO Auto-generated method stub
	
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
	
		Comment comment= this.modelMapper.map(commentDTO, Comment.class);
	
		comment.setPost(post);
		
		Comment savedComment= this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		
		Comment comment= this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Comment Id",commentId));
		
		commentRepo.delete(comment);
		
	}

}
