package com.talentica.blog.serviceImpl;

import com.talentica.blog.entity.Likes;
import com.talentica.blog.entity.Post;
import com.talentica.blog.entity.User;
import com.talentica.blog.exception.ResourceNotFoundException;
import com.talentica.blog.repository.LikesRepo;
import com.talentica.blog.repository.PostRepo;
import com.talentica.blog.repository.UserRepo;
import com.talentica.blog.service.LikesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesServiceImpl implements LikesService {
    private LikesRepo likesRepo;
    private UserRepo userRepo;
    private PostRepo postRepo;

    public LikesServiceImpl(LikesRepo likesRepo, UserRepo userRepo, PostRepo postRepo) {
        this.likesRepo = likesRepo;
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @Override
    public void likeThePostByUser(Integer userId, Integer postId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));

        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id :"+postId));

        Likes like  = new Likes();
        like.setUser(user);
        like.setPost(post);
        likesRepo.save(like);
    }

    @Override
    public void unlikeThePostByUser(Integer userId, Integer postId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));

        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id :"+postId));

        Likes like = likesRepo.findByUserAndPost(user,post);
        likesRepo.deleteById(like.getId());
    }

    @Override
    public Integer getPostLikes(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id :"+postId));

        return likesRepo.findByPost(post).size();
    }

    @Override
    public boolean isPostLikedByUser(Integer postId, Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));

        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id :"+postId));
        Likes like = likesRepo.findByUserAndPost(user,post);
        return like != null;
    }

    @Override
    public List<String> getAllUsersEmailLikedThePost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id :"+postId));
        List<String> emails = post
                .getLikes()
                .stream()
                .map(user->user.getUser().getEmail())
                .collect(Collectors.toList());
        return emails;
    }
}
