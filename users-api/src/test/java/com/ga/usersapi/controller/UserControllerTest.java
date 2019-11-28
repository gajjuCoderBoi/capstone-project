package com.ga.usersapi.controller;

import com.ga.usersapi.config.JwtUtil;
import com.ga.usersapi.exception.LoginException;
import com.ga.usersapi.exception.UserAlreadyExistException;
import com.ga.usersapi.model.User;
import com.ga.usersapi.model.UserRole;
import com.ga.usersapi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @InjectMocks
    UserRole dummyUserRole;

    @Mock
    JwtUtil jwtUtil;

    private MockMvc mockMvc;

    private List<User> dummUserList;

    private User user;

    private String dummyToken = "xyz";

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Before
    public void initDummies() {

        dummUserList = new ArrayList<>();

        user = new User();
        user.setUsername("batman");
        user.setEmail("batman@email.com");
        user.setUserId(1L);
        user.setPassword("xyz");

        User user1 = new User();
        user1.setUserId(2L);
        user1.setEmail("supeman@email.com");
        user1.setUsername("superman");
        User user2 = new User();
        user2.setUserId(3L);
        user2.setEmail("spiderman@email.com");
        user2.setUsername("spiderman");

        dummUserList.add(user1);
        dummUserList.add(user2);

        dummyUserRole.setRoleId(1L);
        dummyUserRole.setName("USER");
        user.getRoles().add(dummyUserRole);

    }


    @Test
    public void signup_Token_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson(user.getEmail(), user.getUsername(), user.getPassword()));


        when(userService.signup(any())).thenReturn(Arrays.asList(user.getEmail(), dummyToken));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"xyz\"}"))
                .andReturn();
    }

    @Test
    public void signup_Exception_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }


    @Test
    public void login_Token_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson(user.getEmail(), user.getUsername(), user.getPassword()));


        when(userService.login(any())).thenReturn(Arrays.asList(user.getEmail(), dummyToken));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"xyz\"}"))
                .andReturn();
    }

    @Test
    public void login_Exception_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void getUser_User_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+dummyToken)
                .contentType(MediaType.APPLICATION_JSON);

        when(userService.getUserByToken(anyString())).thenReturn(user);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

    }

    private static String createUserInJson(String email, String username, String password) {
        return "{ " +
                "\"username\": \"" + username + "\", " +
                " \"email\": \""+ email + "\", " +
                "\"password\":\"" + password + "\"" +
                "}";
    }
}