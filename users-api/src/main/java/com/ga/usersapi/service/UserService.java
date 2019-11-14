package com.ga.usersapi.service;

import com.ga.usersapi.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    public List<User> listUsers();

    public List<String> signup(User user);

    public List<String> login(User user);

    public User getUserByToken(String token);

    public User getUserbyUsername(String username);

    public User getUserbyId(Long id);

    public List<User> userListFromUserIds(List<Long> userIds);

}
