package com.talentica.blog.repository;

import com.talentica.blog.entity.Followers;
import com.talentica.blog.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowersRepo extends JpaRepository<Followers,Integer> {
    Followers findByFromAndTo(User from, User to);

}
