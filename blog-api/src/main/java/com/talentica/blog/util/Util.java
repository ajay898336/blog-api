package com.talentica.blog.util;

import com.talentica.blog.dto.PostDTO;
import com.talentica.blog.dto.UserDTO;
import com.talentica.blog.entity.Post;
import com.talentica.blog.entity.User;

public class Util {

    public static User dtoToUser(UserDTO userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .createdBy(userDto.getCreatedBy())
                .updatedBy(userDto.getUpdatedBy())
                .createdDate(userDto.getCreatedDate())
                .updatedDate(userDto.getUpdatedDate())
                .followersCount(userDto.getFollowersCount())
                .followingCount(userDto.getFollowingCount()).build();
        return user;
    }
    public static UserDTO userToDto(User user) {
        UserDTO userDto = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .build();
        return userDto;
    }

    public static Post dtoToPost(PostDTO postDto) {
        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .createdBy(postDto.getCreatedBy())
                .updatedBy(postDto.getUpdatedBy())
                .createdDate(postDto.getCreatedDate())
                .updatedDate(postDto.getUpdatedDate())
                .build();
        return post;
    }
    public static PostDTO postToDto(Post post) {
        PostDTO postDto = PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdBy(post.getCreatedBy())
                .updatedBy(post.getUpdatedBy())
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getUpdatedDate())
                .build();
        return postDto;
    }

}
