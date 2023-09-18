package com.talentica.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentica.blog.dto.UserDTO;
import com.talentica.blog.dto.UserResponse;
import com.talentica.blog.entity.User;
import com.talentica.blog.repository.H2DBRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private UserDTO user,user1;
    private MockMvc mockMvc;
    @Autowired
   private WebApplicationContext webApplicationContext;
    @Autowired
    private H2DBRepository h2DBRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    @LocalServerPort
    public int port;
    public String baseUrl="http://localhost:";//+port+"/api/users";
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        baseUrl = baseUrl.concat(port+"").concat("/api/users");
        objectMapper.findAndRegisterModules();
        user = UserDTO.builder().firstName("Ajay").lastName("Dawande").email("ajay.dawande@gmail.com").build();
    }
    @AfterEach
    public void init() {
        mockMvc = null;
        user = null;
        h2DBRepository.deleteAll();
    }
    @Test
    void testCanUserCreateByCreateUser() throws Exception {
        String jsonReq = objectMapper. writeValueAsString(user);
        MvcResult result = mockMvc.perform(post(baseUrl)
                .content(jsonReq)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isCreated())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        Map map = objectMapper.readValue(resultContent, Map.class);
        UserResponse userResponse = objectMapper.convertValue(map, UserResponse.class);
        assertThat(userResponse.getStatus()).isEqualTo(201);
        assertThat(userResponse.getMessage()).contains("User created successfully");
        assertThat(userResponse.getUser().getId()).isEqualTo(1);
    }

    @Test
    void testUserShouldNotBeCreateBadRequestFirstNameIsEmptyByCreateUser() throws Exception {
        UserDTO user = UserDTO.builder().firstName("").lastName("Dawande").email("ajay.dawande@gmail.com").build();
        String jsonReq = objectMapper. writeValueAsString(user);
        MvcResult result = mockMvc.perform(post(baseUrl)
                        .content(jsonReq)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        Map map = objectMapper.readValue(resultContent, Map.class);
        assertThat(map.get("status")).isEqualTo(400);
    }
    @Test
    void testUserShouldNotBeCreateBadRequestLastNameIsEmptyByCreateUser() throws Exception {
        user = UserDTO.builder().firstName("Ajay").lastName("").email("ajay.dawande@gmail.com").build();
        String jsonReq = objectMapper. writeValueAsString(user);
        MvcResult result = mockMvc.perform(post(baseUrl)
                        .content(jsonReq)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        Map map = objectMapper.readValue(resultContent, Map.class);
        assertThat(map.get("status")).isEqualTo(400);
    }

    @Test
    void testUserShouldNotBeCreatedBadRequestFirstNameAndLastNameAreEmptyByCreateUser() throws Exception {
        user = UserDTO.builder().firstName("").lastName("").email("ajay.dawande@gmail.com").build();
        String jsonReq = objectMapper. writeValueAsString(user);
        MvcResult result = mockMvc.perform(post(baseUrl)
                        .content(jsonReq)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        Map map = objectMapper.readValue(resultContent, Map.class);
        assertThat(map.get("status")).isEqualTo(400);
    }

    @Test
    void testUserShouldNotBeCreatedBadRequestNotAnEmailByCreateUser() throws Exception {
        UserDTO user = UserDTO.builder().firstName("").lastName("").email("ajay.dawandegmail.com").build();
        String jsonReq = objectMapper. writeValueAsString(user);
        MvcResult result = mockMvc.perform(post(baseUrl)
                        .content(jsonReq)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest())
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        Map map = objectMapper.readValue(resultContent, Map.class);
        assertThat(map.get("status")).isEqualTo(400);

    }
    @Test
    void testUserShouldNotBeCreatedBadRequestEmailAlreadyExistByCreateUser() throws Exception {
        String jsonReq = objectMapper. writeValueAsString(user);
        MvcResult result = mockMvc.perform(post(baseUrl)
                        .content(jsonReq)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isCreated()).andReturn();

        user1 = UserDTO.builder().firstName("AjayKumar").lastName("Dawande").email("ajay.dawande@gmail.com").build();
        jsonReq = objectMapper. writeValueAsString(user1);
         result = mockMvc.perform(post(baseUrl)
                        .content(jsonReq)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUserShouldBeUpdatedByUpdateUser() throws Exception {

        String jsonReq = objectMapper. writeValueAsString(user);
        MvcResult result = mockMvc.perform(post(baseUrl)
                .content(jsonReq)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isCreated()).andReturn();

        String resultContent = result.getResponse().getContentAsString();
        resultContent = result.getResponse().getContentAsString();
        Map map = objectMapper.readValue(resultContent, Map.class);
        Map userMap = (Map) map.get("user");
        UserDTO user = objectMapper.convertValue(userMap, UserDTO.class);
        user.setFirstName("Ajaykumar");
        user.setLastName("Daw");
        jsonReq = objectMapper. writeValueAsString(user);
        baseUrl = baseUrl.concat("/{userId}");
        result = mockMvc.perform(put(baseUrl,user.getId())
                        .content(jsonReq)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk())
                .andReturn();

        resultContent = result.getResponse().getContentAsString();
        map = objectMapper.readValue(resultContent, Map.class);
        assertThat(map.get("status")).isEqualTo(200);
        assertThat(((Map<?, ?>) map.get("user")).get("firstName")).isEqualTo("Ajaykumar");
        assertThat(((Map<?, ?>) map.get("user")).get("lastName")).isEqualTo("Daw");

    }
    @Test
    void testUserShouldNotBeUpdatedIdUserIdIsNotExistByUpdateUser() throws Exception {
        String jsonReq = objectMapper. writeValueAsString(user);
        MvcResult result = mockMvc.perform(post(baseUrl)
                .content(jsonReq)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isCreated()).andReturn();

        String resultContent = result.getResponse().getContentAsString();
        Map map = objectMapper.readValue(resultContent, Map.class);
        Map userMap = (Map) map.get("user");
        user = objectMapper.convertValue(userMap, UserDTO.class);
        user.setFirstName("");
        user.setLastName("");

        jsonReq = objectMapper. writeValueAsString(user);
        baseUrl = baseUrl.concat("/{userId}");
        mockMvc.perform(put(baseUrl,1)
                        .content(jsonReq)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest());
    }

    @Test
    void testUserShouldNotBeUpdatedIfFirstNameOrLastNameOrEmailIsEmptyByUpdateUser() throws Exception {
        String jsonReq = objectMapper. writeValueAsString(user);
        baseUrl = baseUrl.concat("/{userId}");
        mockMvc.perform(put(baseUrl,1)
                .content(jsonReq)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isNotFound());
    }

    @Test
    void testShouldGetAllTheUsersByGetALlUsers() throws Exception {
        User user1 = User.builder().firstName("ajay").lastName("dawande").email("ajay.dawande@gmail.com").build();
        User user2 = User.builder().firstName("akshay").lastName("dawande").email("akshay.dawande@gmail.com").build();
        h2DBRepository.saveAll(List.of(user1,user2));
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGetTheOnlyParticularUserByGetUseryId() throws Exception {
        User user1 = User.builder().firstName("ajay").lastName("dawande").email("ajay.dawande@gmail.com").build();
        User user2 = User.builder().firstName("akshay").lastName("dawande").email("akshay.dawande@gmail.com").build();
        h2DBRepository.saveAll(List.of(user1,user2));
        List<User> users = h2DBRepository.findAll();
        baseUrl = baseUrl.concat("/{userId}");
        MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.get(baseUrl,users.get(0).getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


    }

    @Test
    void testShouldThrowuserNotFOundExceptionByGetUseryId() throws Exception {
        User user1 = User.builder().firstName("ajay").lastName("dawande").email("ajay.dawande@gmail.com").build();
        User user2 = User.builder().firstName("akshay").lastName("dawande").email("akshay.dawande@gmail.com").build();
        h2DBRepository.saveAll(List.of(user1,user2));
        baseUrl = baseUrl.concat("/{userId}");
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl,3)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testShouldDeleteParticularUserByDeleteUseryId() throws Exception {
        User user1 = User.builder().firstName("ajay").lastName("dawande").email("ajay.dawande@gmail.com").build();
        User user2 = User.builder().firstName("akshay").lastName("dawande").email("akshay.dawande@gmail.com").build();
        h2DBRepository.saveAll(List.of(user1,user2));
        baseUrl = baseUrl.concat("/{userId}");
        List<User> users = h2DBRepository.findAll();
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl,users.get(0).getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldThrowUserNotFOundExceptionByDeleteUseryId() throws Exception {
        User user1 = User.builder().firstName("ajay").lastName("dawande").email("ajay.dawande@gmail.com").build();
        User user2 = User.builder().firstName("akshay").lastName("dawande").email("akshay.dawande@gmail.com").build();
        h2DBRepository.saveAll(List.of(user1,user2));
        baseUrl = baseUrl.concat("/{userId}");
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl,3)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}