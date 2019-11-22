package com.ga.usersapi.service;

import com.ga.usersapi.config.JwtUtil;
import com.ga.usersapi.model.User;
import com.ga.usersapi.model.UserRole;
import com.ga.usersapi.repository.UserRepository;
import com.ga.usersapi.repository.UserRoleRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    @Qualifier("UserToPost")
    private Queue queue;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public List<User> listUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<String> signup(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserRole userRole = userRoleRepository.getRoleByName("USER");
        if(userRole==null){
            userRole = new UserRole();
            userRole.setName("USER");
            userRoleRepository.save(userRole);
        }
        user.getRoles().add(userRole);



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
        System.out.println(userIds);
        List<User> userlist = (List<User>) userRepository.findAllById(userIds);
        System.out.println(userlist);
        return userlist;
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
        for (UserRole role: user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
