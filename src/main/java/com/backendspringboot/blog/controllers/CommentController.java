package com.backendspringboot.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendspringboot.blog.payloads.ApiResponse;
import com.backendspringboot.blog.payloads.CommentDTO;
import com.backendspringboot.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
			@PathVariable Integer postId){
		
		CommentDTO createComment= this.commentService.creatComment(commentDTO, postId);
		
		return new ResponseEntity<CommentDTO>(createComment,HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		
		commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted successfully", true),HttpStatus.OK);
		
	}
	
	
}
