package com.ga.usersapi.integration;

import com.ga.usersapi.config.JwtRequestFilter;
import com.ga.usersapi.config.JwtUtil;
import com.ga.usersapi.exception.UserAlreadyExistException;
import com.ga.usersapi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ga.usersapi.repository.UserRoleRepository;
import com.ga.usersapi.model.User;
import com.ga.usersapi.model.UserRole;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTest {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtRequestFilter jwtRequestFilter;



    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;


    @Test
    public void signup_User_Success() {
        User user =  new User();
        user.setEmail("spiderman@email.com");
        user.setUsername("spiderman");
        user.setPassword("spider");
        UserRole userRole = userRoleRepository.getRoleByName("USER");
        if (userRole == null) {
            userRole = new UserRole();
            userRole.setName("USER");
            userRoleRepository.save(userRole);
        }
        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

    }

    @Test(expected = UserAlreadyExistException.class)
    public void signup_User_DuplicateException() throws UserAlreadyExistException {
        User user =  new User();
        user.setEmail("batman@email.com");
        user.setUsername("batman");
        user.setPassword("bat");
        User savedUser = userRepository.getUserByUsername(user.getUsername());
        System.out.println(savedUser);
        if(savedUser!=null) throw new UserAlreadyExistException("username reserved");


    }



}