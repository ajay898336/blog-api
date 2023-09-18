package com.talentica.blog.service;

import java.util.List;

public interface LikesService {
    void likeThePostByUser(Integer userId, Integer postId);
    void unlikeThePostByUser(Integer userId, Integer postId);
    Integer getPostLikes(Integer postId);

    public  boolean isPostLikedByUser(Integer postId, Integer userId);

    public List<String> getAllUsersEmailLikedThePost(Integer postId);
}
