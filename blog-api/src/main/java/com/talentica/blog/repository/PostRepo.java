package com.talentica.blog.repository;

import com.talentica.blog.entity.Post;
import com.talentica.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);


}
