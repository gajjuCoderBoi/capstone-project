package com.ga.usersapi.service;

import com.ga.usersapi.config.JwtUtil;
import com.ga.usersapi.exception.LoginException;
import com.ga.usersapi.exception.UserAlreadyExistException;
import com.ga.usersapi.model.User;
import com.ga.usersapi.model.UserRole;
import com.ga.usersapi.repository.UserRepository;
import com.ga.usersapi.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/*******************************************************************************************************
 * The UserServiceTest class tests the UserService class and its methods
 *  using Mockito and JUnit
 **************************************************************************/

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Spy
    UserRoleRepository userRoleRepository;

    @InjectMocks
    UserRole dummyUserRole;

    @Mock
    JwtUtil jwtUtil;

    private List<User> dummUserList;

    private User user = new User();

    private String encodedPass = "abc";

    private String dummyToken = "xyz";




    @Before
    public void initDummies() {

        dummUserList = new ArrayList<>();

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
    public void listUsers() {
        when(userRepository.findAll()).thenReturn(dummUserList);
        List<User> userList = userService.listUsers();
        assertEquals(dummUserList.size(), userList.size());
    }

    @Test
    public void signup_UserToken_Success() throws UserAlreadyExistException {
        when(bCryptPasswordEncoder.encode(any())).thenReturn(encodedPass);
        when(userRoleRepository.getRoleByName(anyString())).thenReturn(dummyUserRole);
        user.setUserId(null);
        when(userRepository.getUserByUsername(anyString())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(dummyToken);

        List<String> actual = userService.signup(user);

        assertTrue(actual.size()>0);

    }

    @Test(expected = UserAlreadyExistException.class)
    public void signup_Exception_Error() throws UserAlreadyExistException {
        when(bCryptPasswordEncoder.encode(any())).thenReturn(encodedPass);
        when(userRoleRepository.getRoleByName(anyString())).thenReturn(dummyUserRole);
        when(userRepository.getUserByUsername(anyString())).thenReturn(user);

        userService.signup(user);

    }


    @Test
    public void login_UserToken_Success() throws LoginException {
        when(userRepository.getUserByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(dummyToken);
        when(bCryptPasswordEncoder.matches(anyString(),anyString())).thenReturn(true);
        when(bCryptPasswordEncoder.encode(any())).thenReturn(encodedPass);

        List<String> actual = userService.login(user);

        assertTrue(actual.size()>0);
    }

    @Test(expected = LoginException.class)
    public void login_LoginException_Error() throws LoginException {
        when(userRepository.getUserByUsername(anyString())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(anyString(),anyString())).thenReturn(false);

        List<String> actual = userService.login(user);

        assertTrue(actual.size()>0);
    }

    @Test
    public void getUserByToken_User_Success() {
        when(jwtUtil.getUsernameFromToken(anyString())).thenReturn(user.getUsername());
        when(userRepository.getUserByUsername(anyString())).thenReturn(user);

        User actual = userService.getUserByToken(user.getUsername());

        assertEquals(user.getUsername(),actual.getUsername());

    }

    @Test
    public void getUserbyUsername_User_Success() {
        when(userRepository.getUserByUsername(anyString())).thenReturn(user);

        User actual = userService.getUserbyUsername(user.getUsername());

        assertEquals(user.getUsername(),actual.getUsername());
    }

    @Test
    public void getUserbyId_User_Success() {
        when(userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(user));

        User actual = userService.getUserbyId(user.getUserId());

        assertEquals(user.getUsername(),actual.getUsername());
    }

    @Test
    public void userListFromUserIds_List_Success() {
        when(userRepository.findAllById(any())).thenReturn(dummUserList);

        List<User> actual = userService.userListFromUserIds(Arrays.asList(1L,2L));

        assertTrue(actual.size()>0);

    }

    @Test
    public void loadUserByUsername() {
    }
}