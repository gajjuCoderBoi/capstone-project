package com.ga.usersapi.integration;

import com.ga.usersapi.config.JwtRequestFilter;
import com.ga.usersapi.config.JwtUtil;
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

//    private User createUser(){
//
//        User user = new User();
//        UserRole userRole = userRoleRepository.getRoleByName("USER");
//        user.setEmail("batman@ga");
//        user.setPassword("bat");
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//
//        return user;
//    }


    @Test
    public void signup_User_Success() {
        User user = userRepository.getUserByUsername("batman");
        if(user != null) {
            userRepository.delete(user);
       }



//        user = createUser();
//        user = userRepository.save(user);
//        User foundUser = userRepository.getUserByUsername(user.getEmail());

//        assertNotNull(user);
//        assertNotNull(foundUser);
//        assertEquals(user.getUserId(), foundUser.getUserId());
//
//        userRepository.delete(user);
    }


}