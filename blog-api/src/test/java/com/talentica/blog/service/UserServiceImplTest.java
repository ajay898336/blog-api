package com.talentica.blog.service;

import com.talentica.blog.dto.UserDTO;
import com.talentica.blog.entity.Followers;
import com.talentica.blog.entity.User;
import com.talentica.blog.repository.FollowersRepo;
import com.talentica.blog.repository.LikesRepo;
import com.talentica.blog.repository.PostRepo;
import com.talentica.blog.repository.UserRepo;
import com.talentica.blog.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    @Mock
    private UserRepo userRepo;
    @Mock
    private PostRepo postRepo;
    @Mock
    private LikesRepo likesRepo;

    @Mock
    private FollowersRepo followersRepo;
    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private UserService service = new UserServiceImpl(userRepo,postRepo,followersRepo,likesRepo,modelMapper);

    @BeforeEach
    void setUp() {

    }

    @Test
    @Disabled
    void createUser() {
    }

    @Test
    @Disabled
    void updateUser() {
    }

    @Test
    void getAllUsers() {
        UserDTO user = UserDTO.builder()
                .id(1).firstName("Ajay").lastName("Dawande").email("ajay.dawande.@gmail.com").build();
        UserDTO user2 = UserDTO.builder()
                .id(2).firstName("Akshay").lastName("Dawande").email("akshay.dawande.@gmail.com").build();

        when(service.getAllUsers()).thenReturn((List.of(user,user2)));
        int count = service.getAllUsers().size();
        assertThat(count).isEqualTo(2);

    }

    @Test
    @Disabled
    void getUserById() {
        UserDTO user = UserDTO.builder()
                .id(1).firstName("Ajay").lastName("Dawande").email("ajay.dawande.@gmail.com").build();

        when(service.getUserById(1)).thenReturn(user);
        UserDTO userById = service.getUserById(1);
        assertThat(userById.getFirstName()).isEqualTo("Ajay");

    }

    @Test
    @Disabled
    void deleteUserById() {
    }


}