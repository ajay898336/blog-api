package com.talentica.blog.repository;

import com.talentica.blog.entity.Likes;
import com.talentica.blog.entity.Post;
import com.talentica.blog.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikesRepo extends JpaRepository<Likes,Integer> {

    Likes findByUserAndPost(User user, Post post);

    List<Likes> findByPost(Post post);
}
