package com.talentica.blog.controller;

import com.talentica.blog.dto.PostDTO;
import com.talentica.blog.dto.PostResponse;
import com.talentica.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/user/{userId}", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostDTO post, @PathVariable Integer userId) {
        PostResponse postResponse = new PostResponse();
        PostDTO savedPost = postService.createPost(post,userId);
        postResponse.setStatus(HttpStatus.CREATED.value());
        postResponse.setMessage("Post created successfully with id :"+savedPost.getId());
        postResponse.setPost(savedPost);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{postId}", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<PostResponse> updatePost(@Valid @RequestBody PostDTO post, @PathVariable Integer postId) {
        PostResponse postResponse = new PostResponse();
        PostDTO updatedPost = postService.updatePost(post, postId);
        postResponse.setStatus(HttpStatus.OK.value());
        postResponse.setMessage("Post updated successfully for id :"+updatedPost.getId());
        postResponse.setPost(updatedPost);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Map<String,Object>> getAllPosts(@RequestParam(value = "pageNumber" , defaultValue = "1" ,required = false) Integer pageNumber ,
                                                          @RequestParam(value = "pageSize",defaultValue = "5", required = false) Integer pageSize) {
        Map<String,Object> response = new HashMap<>();
        List<PostDTO> allPosts = postService.getAllPosts(pageNumber, pageSize);
        response.put("status",HttpStatus.OK.value());
        response.put("message","Posts fetched successfully");
        response.put("posts",allPosts);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}", produces = "application/json")
    public ResponseEntity<Map<String,Object>> getPostById(@PathVariable Integer postId) {
        Map<String,Object> response = new HashMap<>();
        PostDTO postById = postService.getPostById(postId);
        response.put("status",HttpStatus.OK.value());
        response.put("message","Post fetched successfully for id :"+postId);
        response.put("post",postById);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}" , produces = "application/json")
    public ResponseEntity<Map<String,Object>> getALlPostByUser(@PathVariable Integer userId) {
        Map<String,Object> response = new HashMap<>();
        List<PostDTO> allPostByUser = postService.getAllPostByUser(userId);
        response.put("status",HttpStatus.OK.value());
        response.put("message","Posts by user fetched successfully by user id :"+userId);
        response.put("post",allPostByUser);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{postId}", produces = "application/json")
    public ResponseEntity<Map<String,Object>> deletePostById(@PathVariable Integer postId) {
        Map<String,Object> response = new HashMap<>();
        postService.deletePostById(postId);
        response.put("status",HttpStatus.OK.value());
        response.put("message","Post deleted successfully for id :"+postId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
