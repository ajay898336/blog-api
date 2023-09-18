package com.talentica.blog.serviceImpl;

import com.talentica.blog.dto.FollowersResponse;
import com.talentica.blog.entity.Followers;
import com.talentica.blog.entity.User;
import com.talentica.blog.exception.BadRequestException;
import com.talentica.blog.exception.ResourceNotFoundException;
import com.talentica.blog.repository.FollowersRepo;
import com.talentica.blog.repository.UserRepo;
import com.talentica.blog.service.FollowersService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowersServiceImpl implements FollowersService {

    private FollowersRepo followersRepo;
    private UserRepo userRepo;

    public FollowersServiceImpl(FollowersRepo followersRepo,UserRepo userRepo) {
        this.followersRepo = followersRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    @Modifying
    @Override
    public void followUser(Integer userIdFrom, Integer userIdTo) {
        FollowersResponse followersResponse = new FollowersResponse();
        if(userIdFrom == null || userIdTo == null) {
            throw new BadRequestException("User id's can not be null");
        }
        if(userIdFrom.equals(userIdTo)) {
            throw new BadRequestException("User can  not follows himself");
        }
        boolean isAlreadyFollowing  = isUserFollowing(userIdFrom,userIdTo);
        if(isAlreadyFollowing) {
            throw new BadRequestException("User with id :" + userIdFrom + " already follows the user with user id :" + userIdTo);
        }

        User userFrom = userRepo.findById(userIdFrom)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userIdFrom));

        User userTo = userRepo.findById(userIdTo)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userIdTo));
        Followers followers = new Followers();
        followers.setFrom(userFrom);
        followers.setTo(userTo);
        followersRepo.save(followers);

        userFrom.setFollowingCount(userFrom.getFollowingCount()+1);
        userTo.setFollowersCount(userTo.getFollowersCount()+1);
        userRepo.saveAll(List.of(userFrom,userTo));
    }

    @Transactional
    @Modifying
    public void unFollowUser(Integer userIdFrom, Integer userIdTo) {
        FollowersResponse followersResponse = new FollowersResponse();
        if(userIdFrom == null || userIdTo == null) {
            throw new BadRequestException("User id's can not be null");
        }
        if(userIdFrom.equals(userIdTo)) {
            throw new BadRequestException("User can  not unfollows himself");
        }
        boolean isAlreadyFollowing  = isUserFollowing(userIdFrom,userIdTo);

        if(!isAlreadyFollowing) {
            throw new BadRequestException("User with id :" + userIdFrom + " does not follows the user with user id :" + userIdTo);
        }

        User userFrom = userRepo.findById(userIdFrom)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userIdFrom));

        User userTo = userRepo.findById(userIdTo)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userIdTo));
        Followers followers = followersRepo.findByFromAndTo(userFrom,userTo);
        followersRepo.deleteById(followers.getId());

        userFrom.setFollowingCount(userFrom.getFollowingCount()-1);
        userTo.setFollowersCount(userTo.getFollowersCount()-1);
        userRepo.saveAll(List.of(userFrom,userTo));

    }

    @Override
    public Integer getFollowersCount(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        return user.getFollowersCount();
    }

    @Override
    public Integer getFollowingCount(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        return user.getFollowingCount();
    }

    @Override
    public List<String> getFollowersEmailOfUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        return user.getFollowers().stream()
                .map(followers -> followers.getFrom())
                .map(usr->usr.getEmail())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getFollowingEmailOfUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));
        return user.getFollowing().stream()
                .map(followers -> followers.getTo())
                .map(usr->usr.getEmail())
                .collect(Collectors.toList());
    }

    public  boolean isUserFollowing(Integer userIdFrom, Integer userIdTo) {
        boolean isFollowing = false;
        User userFrom = userRepo.findById(userIdFrom)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userIdFrom));

        User userTo = userRepo.findById(userIdTo)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userIdTo));
        Followers followers = followersRepo.findByFromAndTo(userFrom,userTo);
        return followers != null;

    }
}
