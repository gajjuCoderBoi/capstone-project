package com.ga.usersapi.controller;


import com.ga.usersapi.config.JwtUtil;
import com.ga.usersapi.model.JwtResponse;
import com.ga.usersapi.model.User;
import com.ga.usersapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
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
