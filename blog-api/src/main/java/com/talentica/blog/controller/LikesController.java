package com.talentica.blog.controller;

import com.talentica.blog.dto.LikesReqDTO;
import com.talentica.blog.dto.LikesResponse;
import com.talentica.blog.service.LikesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikesController {

    private LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping(value = "/like",consumes = "application/json" , produces = "application/json")
    public ResponseEntity<LikesResponse> likeUser(@RequestBody LikesReqDTO reqDto) {
        LikesResponse likesResponse = new LikesResponse();
        boolean isAlreadyLiked  = likesService.isPostLikedByUser(reqDto.getPostId(),reqDto.getUserId());
        if(!isAlreadyLiked) {
            likesService.likeThePostByUser(reqDto.getUserId(), reqDto.getPostId());
            likesResponse.setStatus(HttpStatus.CREATED.value());
            likesResponse.setMessage("User with id :" + reqDto.getUserId() + " Like the post with post id :" + reqDto.getPostId());
        } else {
            likesResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            likesResponse.setMessage("User with id :" + reqDto.getUserId() + " already Likes the post with post id :" + reqDto.getPostId());
            return new ResponseEntity<>(likesResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(likesResponse, HttpStatus.CREATED);
    }
    @PutMapping(value = "/unlike",consumes = "application/json" , produces = "application/json")
    public ResponseEntity<LikesResponse> unLikeUser(@RequestBody LikesReqDTO reqDto) {
        LikesResponse likesResponse = new LikesResponse();
        boolean isAlreadyLiked  = likesService.isPostLikedByUser(reqDto.getPostId(),reqDto.getUserId());

        if(isAlreadyLiked) {
            likesService.unlikeThePostByUser(reqDto.getUserId(), reqDto.getPostId());
            likesResponse.setStatus(HttpStatus.OK.value());
            likesResponse.setMessage("User with id :" + reqDto.getUserId() + " unlike the post with post id :" + reqDto.getPostId());
        } else {
            likesResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            likesResponse.setMessage("User with id :" + reqDto.getUserId() + " did not  Liked the post with post id :" + reqDto.getPostId());
            return new ResponseEntity<>(likesResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(likesResponse, HttpStatus.OK);
    }
    @GetMapping(value = "/post/{postId}", produces = "application/json")
    public ResponseEntity<Map<String,Integer>> getPostLikes(@PathVariable Integer postId) {
        Map<String,Integer> response = new HashMap<>();
        Integer likesCount = likesService.getPostLikes(postId);
        response.put("status",HttpStatus.OK.value());
        response.put("likes",likesCount);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/post/{postId}/userEmails", produces = "application/json")
    public ResponseEntity<Map<String,Object>> getAllUsersEmailLikedThePost(@PathVariable Integer postId) {
        Map<String,Object> response = new HashMap<>();
        List<String> allUsersEmailLikedThePost = likesService.getAllUsersEmailLikedThePost(postId);
        response.put("status",HttpStatus.OK.value());
        response.put("emailsOfUserLikedThePost",allUsersEmailLikedThePost);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
