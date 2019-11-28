package com.ga.usersapi.controller;

import com.ga.usersapi.config.JwtUtil;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    private List<User> dummUserList;

    private User user;

    private String encodedPass = "abc";

    private String dummyToken = "xyz";




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
    public void signup_Token_Success() throws UserAlreadyExistException {
    }

    @Test
    public void login() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void userListFromUserIds() {
    }
}