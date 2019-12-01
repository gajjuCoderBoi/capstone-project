package com.ga.usersapi.service;

import com.ga.usersapi.config.JwtUtil;
import com.ga.usersapi.exception.LoginException;
import com.ga.usersapi.exception.UserAlreadyExistException;
import com.ga.usersapi.model.User;
import com.ga.usersapi.model.UserRole;
import com.ga.usersapi.repository.UserRepository;
import com.ga.usersapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public List<User> listUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<String> signup(User user) throws UserAlreadyExistException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserRole userRole = userRoleRepository.getRoleByName("USER");
        if (userRole == null) {
            userRole = new UserRole();
            userRole.setName("USER");
            userRoleRepository.save(userRole);
        }
        user.getRoles().add(userRole);
        User savedUser = getUserbyUsername(user.getUsername());
        if (savedUser != null) {
            logger.info("Failed User Signing Up : {}", user);
            throw new UserAlreadyExistException("User with this username already exist.");
        }
        savedUser = userRepository.save(user);
        if (savedUser.getEmail() != null) {
            UserDetails userDetails = loadUserByUsername(user.getEmail());
            logger.info("User SignedUp: {}", user);
            return Arrays.asList(user.getUsername(), jwtUtil.generateToken(userDetails));
        }

        return Collections.emptyList();
    }

    @Override
    public List<String> login(User user) throws LoginException {
        User foundUser = userRepository.getUserByUsername(user.getEmail());
        if (foundUser != null &&
                bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            UserDetails userDetails = loadUserByUsername(foundUser.getEmail());
            logger.info("User LoggedIn: {}", foundUser);
            return Arrays.asList(user.getUsername(), jwtUtil.generateToken(userDetails));
        } else if (foundUser != null &&
                !bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())
        ) {
            logger.info("Failed User Logged Up : {}", foundUser);
            throw new LoginException("Invalid Username and Password.");
        }

        return Collections.emptyList();
    }

    @Override
    public User getUserByToken(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        return getUserbyUsername(username);
    }

    @Override
    public User getUserbyUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User getUserbyId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> userListFromUserIds(List<Long> userIds) {
        return (List<User>) userRepository.findAllById(userIds);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()),
                true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
