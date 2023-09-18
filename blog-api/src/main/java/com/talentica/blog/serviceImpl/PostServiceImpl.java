package com.talentica.blog.serviceImpl;

import com.talentica.blog.dto.PostDTO;
import com.talentica.blog.entity.Likes;
import com.talentica.blog.entity.Post;
import com.talentica.blog.entity.User;
import com.talentica.blog.exception.ResourceNotFoundException;
import com.talentica.blog.repository.LikesRepo;
import com.talentica.blog.repository.PostRepo;
import com.talentica.blog.repository.UserRepo;
import com.talentica.blog.service.PostService;
import com.talentica.blog.util.Util;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepo postRepo;
    private UserRepo userRepo;
    private LikesRepo likesRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepo postRepo, UserRepo userRepo, LikesRepo likesRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.likesRepo = likesRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Modifying
    @Override
    public PostDTO createPost(PostDTO postDto, Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        Post post = Util.dtoToPost(postDto);
        post.setUser(user);
        post.setCreatedBy(user.getEmail());
        Post savedPost = postRepo.save(post);
        savedPost.setUpdatedDate(null);
        return Util.postToDto(savedPost);
    }

    @Override
    public PostDTO updatePost(PostDTO postDto, Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id :"+postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUpdatedBy(post.getUser().getEmail());
        Post updatedPost = postRepo.save(post);
        return Util.postToDto(updatedPost);
    }

    @Override
    public List<PostDTO> getAllPosts(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Post> pageData = postRepo.findAll(pageRequest);
        List<Post> allPost = pageData.getContent();
        return allPost
                .stream()
                .map(post->Util.postToDto(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id :"+postId));
        return Util.postToDto(post);
    }

    @Override
    public List<PostDTO> getAllPostByUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        List<Post> allPosts = postRepo.findByUser(user);
        return allPosts
                .stream()
                .map(post->Util.postToDto(post))
                .collect(Collectors.toList());
    }

    @Transactional
    @Modifying
    public void deletePostById(Integer postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post not found with id :"+postId));
        User user = post.getUser();
        List<Likes> likes = post.getLikes();
        likesRepo.deleteAll(likes);
        postRepo.delete(post);
    }
}
