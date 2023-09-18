package com.talentica.blog.repository;

import com.talentica.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface H2DBRepository extends JpaRepository<User,Integer> {
}
