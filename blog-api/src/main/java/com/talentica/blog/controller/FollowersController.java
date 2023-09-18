package com.talentica.blog.controller;

import com.talentica.blog.dto.FollowersReqDTO;
import com.talentica.blog.dto.FollowersResponse;
import com.talentica.blog.service.FollowersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/followers")
public class FollowersController {

    private FollowersService followersService;

    public FollowersController(FollowersService followersService) {
        this.followersService = followersService;
    }

    @PostMapping(value = "/follow",consumes = "application/json" , produces = "application/json")
    public ResponseEntity<FollowersResponse> followUser(@RequestBody FollowersReqDTO reqDto) {
        followersService.followUser(reqDto.getUserIdFrom(), reqDto.getUserIdTo());
        FollowersResponse followersResponse = new FollowersResponse();
        followersResponse.setStatus(HttpStatus.CREATED.value());
        followersResponse.setMessage("User with id :" + reqDto.getUserIdFrom() + " started following the user with user id :" + reqDto.getUserIdTo());
        return new ResponseEntity<>(followersResponse,HttpStatus.CREATED);
    }
    @PutMapping(value = "/unfollow",consumes = "application/json" , produces = "application/json")
    public ResponseEntity<FollowersResponse> unFollowUser(@RequestBody FollowersReqDTO reqDto) {
        followersService.unFollowUser(reqDto.getUserIdFrom(), reqDto.getUserIdTo());
        FollowersResponse followersResponse = new FollowersResponse();
        followersResponse.setStatus(HttpStatus.OK.value());
        followersResponse.setMessage("User with id :" + reqDto.getUserIdFrom() + " unfollows the user with user id :" + reqDto.getUserIdTo());
        return new ResponseEntity<>(followersResponse,HttpStatus.OK);
    }
    @GetMapping(value = "/Userfollowers/{userId}", produces = "application/json")
    public ResponseEntity<Map<String,Integer>> getFollowersCount(@PathVariable Integer userId) {
        Map<String,Integer> response = new HashMap<>();
        Integer followersCount = followersService.getFollowersCount(userId);
        response.put("followers",followersCount);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping(value = "/Userfollowing/{userId}", produces = "application/json")
    public ResponseEntity<Map<String,Integer>> getFollowingCOunt(@PathVariable Integer userId) {
        Map<String,Integer> response = new HashMap<>();
        Integer followingCount = followersService.getFollowingCount(userId);
        response.put("following",followingCount);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping(value = "/userFollowers/emails/{userId}", produces = "application/json")
    public ResponseEntity<Map<String,Object>> getFollowersEmailOfUser(@PathVariable Integer userId) {
        Map<String,Object> response = new HashMap<>();
        List<String> followersEmail = followersService.getFollowersEmailOfUser(userId);
        response.put("status",HttpStatus.OK.value());
        response.put("followers",followersEmail);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/userFollowing/emails/{userId}", produces = "application/json")
    public ResponseEntity<Map<String,Object>> getFollowingEmailOfUser(@PathVariable Integer userId) {
        Map<String,Object> response = new HashMap<>();
        List<String> followingEmail = followersService.getFollowingEmailOfUser(userId);
        response.put("status",HttpStatus.OK.value());
        response.put("followers",followingEmail);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
