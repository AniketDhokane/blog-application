package com.backendspringboot.blog.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backendspringboot.blog.entities.Category;
import com.backendspringboot.blog.entities.Post;
import com.backendspringboot.blog.entities.User;
import com.backendspringboot.blog.exceptions.ResourceNotFoundException;
import com.backendspringboot.blog.payloads.PostDTO;
import com.backendspringboot.blog.payloads.PostResponse;
import com.backendspringboot.blog.repositories.CategoryRepo;
import com.backendspringboot.blog.repositories.PostRepo;
import com.backendspringboot.blog.repositories.UserRepo;
import com.backendspringboot.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId ) {
		
	 User user = this.userRepo.findById(userId).orElseThrow((()-> new ResourceNotFoundException("User", "User Id", userId)));
	
	Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
	Post post =this.modelMapper.map(postDTO,Post.class); // convert from postDTO object to post object 
	
	post.setImageName("default.png");
	post.setAddDate(new Date());
	post.setUser(user);
	post.setCategory(category);
	
	Post newPost=postRepo.save(post);
	
	
	return this.modelMapper.map(newPost,PostDTO.class);
	
	
	
		
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		// TODO Auto-generated method stub
		
		Post post= postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());

		
		
		Post savedPost=postRepo.save(post);
		
		PostDTO postDTO2= this.modelMapper.map(savedPost, PostDTO.class);
		
		
		return postDTO2;
	}

	@Override
	public void deletePost(Integer postId) {
		
		// TODO Auto-generated method stub
		
		Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		
		this.postRepo.delete(post);
	

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		/*if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}*/
		
		Pageable p=  PageRequest.of(pageNumber,pageSize,sort);// we can use the descending and ascending also 
		
		Page<Post> pagePosts= this.postRepo.findAll(p);
		
		List<Post>  allPost= pagePosts.getContent();
		
		List<PostDTO> postDTOs= allPost.stream().map((post)-> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	 
		PostResponse postResponse= new PostResponse();
		
		postResponse.setContent(postDTOs);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse ; 
	}
	
	@Override
	public PostDTO getPostById(Integer postId) {
		
		Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		PostDTO postDTO= this.modelMapper.map(post, PostDTO.class);
		
		return postDTO;
		
	}
	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
	Category category=	this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
	
	List<Post> posts= this.postRepo.findAllByCategory(category);
	
	List<PostDTO> postDTOs=	posts.stream().map((post)->this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
	
		return postDTOs;
	}

	@Override
	public List<PostDTO> getPostsByUsers(Integer userId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id", userId));
		
		List<Post> posts= this.postRepo.findAllByUser(user);
		
		List<PostDTO> postDTOs= posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
		 return postDTOs;
		
	}

	@Override
	public List<PostDTO> searchPost(String keyword) {
		// TODO Auto-generated method stub
		
		List<Post> posts=	this.postRepo.findByTitle("%"+keyword+"%");
		
		List<PostDTO> postDTO= posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
		return postDTO;
	}

}
