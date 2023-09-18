package com.talentica.blog.serviceImpl;

import com.talentica.blog.dto.UserDTO;
import com.talentica.blog.entity.Followers;
import com.talentica.blog.entity.Likes;
import com.talentica.blog.entity.Post;
import com.talentica.blog.entity.User;
import com.talentica.blog.exception.ResourceNotFoundException;
import com.talentica.blog.repository.FollowersRepo;
import com.talentica.blog.repository.LikesRepo;
import com.talentica.blog.repository.PostRepo;
import com.talentica.blog.repository.UserRepo;
import com.talentica.blog.service.UserService;
import com.talentica.blog.util.Util;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private PostRepo postRepo;
    private FollowersRepo followersRepo;
    private LikesRepo likesRepo;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepo userRepo, PostRepo postRepo, FollowersRepo followersRepo, LikesRepo likesRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.followersRepo = followersRepo;
        this.likesRepo = likesRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDto) {
        User user = Util.dtoToUser(userDto);
        user.setCreatedBy(user.getEmail());
        User savedUser = userRepo.save(user);
        savedUser.setUpdatedDate(null);
        return Util.userToDto(savedUser);
    }
    @Transactional
    @Modifying
    @Override
    public UserDTO updateUser(UserDTO userDto, Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUpdatedBy(userDto.getEmail());
        User updatedUser = userRepo.save(user);
        return Util.userToDto(updatedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(user->Util.userToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        return Util.userToDto(user);
    }

    @Transactional
    @Modifying
    @Override
    public void deleteUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        List<Post> posts = postRepo.findByUser(user);
        List<Followers> followers = user.getFollowers();
        List<Followers> followings = user.getFollowing();
        List<Likes> likes = user.getLikes();
        likesRepo.deleteAll(likes);
        followersRepo.deleteAll(followings);
        followersRepo.deleteAll(followers);
        postRepo.deleteAll(posts);
        userRepo.delete(user);

        List<User> followerUsers = user.getFollowers().stream().map(usr->usr.getFrom()).collect(Collectors.toList());
        List<User> followingUsers = user.getFollowing().stream().map(usr->usr.getTo()).collect(Collectors.toList());
        for(User usr : followerUsers) {
            usr.setFollowingCount(usr.getFollowingCount()-1);
            usr.setFollowing(null);
            userRepo.save(usr);
        }
        for(User usr : followingUsers) {
            usr.setFollowersCount(usr.getFollowersCount()-1);
            usr.setFollowers(null);
            userRepo.save(usr);
        }
    }
}
