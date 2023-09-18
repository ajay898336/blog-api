package com.talentica.blog.repository;

import com.talentica.blog.entity.Followers;
import com.talentica.blog.entity.Likes;
import com.talentica.blog.entity.Post;
import com.talentica.blog.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class FollowersRepoTest {

    @Autowired
    private FollowersRepo followersRepo;
    @Autowired
    private UserRepo userRepo;


    Followers followers;
    User user,user1,user2;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("Ajay");
        user.setLastName("Dawande");
        user.setEmail("ajay.dawande@gmail.com");
        user.setCreatedBy("ajay.dawande@gmail.com");
        user.setUpdatedBy("ajay.dawande@gmail.com");
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());

        user1 = new User();
        user1.setFirstName("Akshay");
        user1.setLastName("Dawande");
        user1.setEmail("akshay.dawande@gmail.com");
        user1.setCreatedBy("akshay.dawande@gmail.com");
        user1.setUpdatedBy("akshay.dawande@gmail.com");
        user1.setCreatedDate(LocalDateTime.now());
        user1.setUpdatedDate(LocalDateTime.now());

        user2 = new User();
        user2.setFirstName("Lalu");
        user2.setLastName("Patil");
        user2.setEmail("lalu.patil@gmail.com");
        user2.setCreatedBy("lalu.patil@gmail.com");
        user2.setUpdatedBy("lalu.patil@gmail.com");
        user2.setCreatedDate(LocalDateTime.now());
        user2.setUpdatedDate(LocalDateTime.now());

        userRepo.save(user);
        userRepo.save(user1);
        userRepo.save(user2);

        followers = new Followers();
        followers.setFrom(user);
        followers.setTo(user1);
        followersRepo.save(followers);
    }

    @AfterEach
    void tearDown() {
        followersRepo.deleteAll();
        userRepo.deleteAll();
    }

    //Test case success
    @Test
    void testFindByFromAndToFoundCase() {
        Followers byFromAndTo = followersRepo.findByFromAndTo(user, user1);
        assertThat(byFromAndTo.getId()).isEqualTo(followers.getId());
    }

    //Test case failed
    @Test
    void testFindByFromAndToNotFoundCase() {
        Followers byFromAndTo = followersRepo.findByFromAndTo(user, user2);
        assertThat(byFromAndTo).isNull();
    }
}