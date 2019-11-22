package com.ga.usersapi.controller;


import com.ga.usersapi.config.JwtUtil;
import com.ga.usersapi.exception.LoginException;
import com.ga.usersapi.model.JwtResponse;
import com.ga.usersapi.model.User;
import com.ga.usersapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/test")
    public String test(){
        return "Test Post";
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody User user) throws LoginException {
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
    }

    @GetMapping
    public ResponseEntity getUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getUserByToken(token));
    }

    @PostMapping("/userlist")
    public ResponseEntity userListFromUserIds(@RequestBody List<Long> list){
        return ResponseEntity.ok(userService.userListFromUserIds(list));
    }



    /*@PutMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody User user, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(new JwtResponse(userService.update(user, token)));
    }*/

    /*@PostMapping("/profile")
    public UserProfile createProfile(@RequestBody UserProfile userProfile, @RequestHeader("Authorization") String token){
        return userProfileService.createUserProfile(userProfile, jwtUtil.getUsernameFromToken(token));
    }

    @PutMapping("/profile")
    public UserProfile updateProfile(@RequestBody UserProfile userProfile, @RequestHeader("Authorization") String token){
        return userProfileService.updateProfile(userProfile, jwtUtil.getUsernameFromToken(token));
    }*/

}
