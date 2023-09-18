package com.talentica.blog.service;

import java.util.List;

public interface FollowersService {
    void followUser(Integer userIdFrom, Integer userIdTo);
    void unFollowUser(Integer userIdFrom, Integer userIdTo);
    Integer getFollowersCount(Integer userId);
    Integer getFollowingCount(Integer userId);
    List<String> getFollowersEmailOfUser(Integer userId);
    List<String> getFollowingEmailOfUser(Integer userId);
    public  boolean isUserFollowing(Integer userIdFrom, Integer userIdTo);
}
