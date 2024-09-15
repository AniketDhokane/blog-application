package com.backendspringboot.blog.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backendspringboot.blog.config.AppConstants;
import com.backendspringboot.blog.payloads.ApiResponse;
import com.backendspringboot.blog.payloads.PostDTO;
import com.backendspringboot.blog.payloads.PostResponse;
import com.backendspringboot.blog.services.FileService;
import com.backendspringboot.blog.services.PostService;



@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired	
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(
			@RequestBody PostDTO postDTO,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		
		PostDTO createdPostDTO=this.postService.createPost(postDTO, userId, categoryId);
			
		return new ResponseEntity<PostDTO>(createdPostDTO,HttpStatus.CREATED);
	}
	
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		
		this.postService.deletePost(postId);
		
		return new ApiResponse("Post is Successfully Deleted",true);
		}
	
	//update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,@PathVariable Integer postId){
		
		PostDTO updetedPostDTO= this.postService.updatePost(postDTO, postId);
		
		return new ResponseEntity<PostDTO>(updetedPostDTO,HttpStatus.OK);
	}
	
	
	//get post by Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
		
		PostDTO postDTO= this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDTO>(postDTO,HttpStatus.OK);
	}
	
// get by user	
  @GetMapping("/user/{userId}/posts")
  public ResponseEntity<List<PostDTO>> getPostsByUsers(@PathVariable Integer userId){
	  
	  List<PostDTO> posts= this.postService.getPostsByUsers(userId);
	  
	  return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
  }

  
  // get by category
  @GetMapping("/category/{categoryId}/posts")
  public ResponseEntity<List<PostDTO>> getPostsByCategories(@PathVariable Integer categoryId)
  {
	  List<PostDTO> posts= this.postService.getPostsByCategory(categoryId);
	  
	  return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);  
  }

  
  @GetMapping("/posts")
  public ResponseEntity<PostResponse> getAllPost(
		  @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
		  @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
  		  @RequestParam(value= "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
  		  @RequestParam(value= "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
  {
	  
	 PostResponse postResponse= this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
	  
	  return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
  }
  
  @GetMapping("/posts/search/{keywords}") 
  public ResponseEntity<List<PostDTO>> searchPostByTitle(
		  @PathVariable("keywords") String keywords){
	  
	  List<PostDTO> result=this.postService.searchPost(keywords);
	  
	  return new ResponseEntity<List<PostDTO>>(result,HttpStatus.OK);
  }
  
  
  //Post image upload
  
  @PostMapping("/post/image/upload/{postId}")
  public ResponseEntity<PostDTO> uploadPostImage(
		  @RequestParam("image") MultipartFile image,
		  @PathVariable Integer postId) throws IOException{

	  PostDTO postDTO= this.postService.getPostById(postId);

	  String fileName=  this.fileService.uploadImage(path, image);
	
	postDTO.setImageName(fileName);
	PostDTO updateDto=	this.postService.updatePost(postDTO, postId);
   
		return new ResponseEntity<PostDTO>(updateDto,HttpStatus.OK);
  }
  
  //method to serve files
 /* @GetMapping(value = "post/image/{imageName}", produces=MediaType.IMAGE_JPEG_VALUE)
  public void downloadImage(
		  @PathVariable("imageName") String imageName,
		  HttpServletResponse response)throws IOException{
	  
	  InputStream resource = this.fileService.getResource(path, imageName);
	  response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	  StreamUtils.copy(resource,response.getOutputStream());
  }*/
  
}  
  


