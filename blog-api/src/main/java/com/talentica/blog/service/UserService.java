package com.talentica.blog.service;

import com.talentica.blog.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDto);
    UserDTO updateUser(UserDTO userDto, Integer userId);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Integer userId);

    void deleteUserById(Integer userId);

}
