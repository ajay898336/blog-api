package com.talentica.blog.controller;

import com.talentica.blog.dto.UserDTO;
import com.talentica.blog.dto.UserResponse;
import com.talentica.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json" , produces = "application/json")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDTO user) {
        UserResponse userResponse = new UserResponse();
        UserDTO savedUser = userService.createUser(user);
        userResponse.setStatus(HttpStatus.CREATED.value());
        userResponse.setMessage("User created successfully with id :"+savedUser.getId());
        userResponse.setUser(savedUser);
        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}", consumes = "application/json" , produces = "application/json")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserDTO user, @PathVariable Integer userId) {
        UserResponse userResponse = new UserResponse();
        UserDTO updatedUser = userService.updateUser(user,userId);
        userResponse.setStatus(HttpStatus.OK.value());
        userResponse.setMessage("Successfully updated user details for id :"+userId);
        userResponse.setUser(updatedUser);
        return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Map<String,Object>> getAllUsers() {
        Map<String,Object> response = new HashMap<>();
        List<UserDTO> allUsers = userService.getAllUsers();
        response.put("status" , HttpStatus.OK.value());
        response.put("users" , allUsers);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<Map<String,Object>> getUserById(@PathVariable Integer userId) {
        Map<String,Object> response = new HashMap<>();
        UserDTO userById = userService.getUserById(userId);
        response.put("status",HttpStatus.OK.value());
        response.put("message","Successfully fetched user details for id :"+userId);
        response.put("user",userById);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<Map<String,Object>> deleteUserById(@PathVariable Integer userId) {
        Map<String,Object> response = new HashMap<>();
        userService.deleteUserById(userId);
        response.put("status",HttpStatus.OK.value());
        response.put("message","User deleted Successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
