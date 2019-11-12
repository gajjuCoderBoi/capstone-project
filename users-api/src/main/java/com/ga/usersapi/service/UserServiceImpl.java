package com.ga.usersapi.service;

import com.ga.usersapi.config.JwtUtil;
import com.ga.usersapi.model.User;
import com.ga.usersapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<String> signup(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if (userRepository.save(user).getUserId() != null) {
            UserDetails userDetails = loadUserByUsername(user.getEmail());
            return Arrays.asList(user.getEmail(), jwtUtil.generateToken(userDetails));        }

        return null;
    }

    @Override
    public List<String> login(User user) {
        User foundUser = userRepository.getUserByUsername(user.getEmail());
        if(foundUser != null &&
                foundUser.getUserId() != null &&
                bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            UserDetails userDetails = loadUserByUsername(foundUser.getEmail());
            return Arrays.asList(user.getEmail(), jwtUtil.generateToken(userDetails));

        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getUserByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Unknown user: " + username);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()),
                true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }
}
