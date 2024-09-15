package com.backendspringboot.blog.services;

import com.backendspringboot.blog.payloads.CommentDTO;

public interface CommentService {
	
	CommentDTO creatComment(CommentDTO commentDTO, Integer postId);
	
	void deleteComment(Integer commentId);

}
