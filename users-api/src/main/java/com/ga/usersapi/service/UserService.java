package com.ga.usersapi.service;

import com.ga.usersapi.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    public List<User> listUsers();

    public List<String> signup(User user);

    public List<String> login(User user);

}
