package com.talentica.blog.service;

import com.talentica.blog.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO post, Integer userId);
    PostDTO updatePost(PostDTO post, Integer postId);

    List<PostDTO> getAllPosts(Integer pageNumber, Integer pageSize);
    PostDTO getPostById(Integer postId);

    List<PostDTO> getAllPostByUser(Integer postId);

    void deletePostById(Integer postId);
}
