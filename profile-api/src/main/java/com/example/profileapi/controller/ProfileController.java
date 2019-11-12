package com.example.profileapi.controller;

import com.example.profileapi.model.Profile;
import com.example.profileapi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("/test")
    public String test(){
        return "This is Profile API";
    }

    @GetMapping("")
    public ResponseEntity<?> getProfile(){
        return null;
    }

    @PostMapping("")
    public ResponseEntity<?> createProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token){
        System.out.println(token);
        return null;
    }

    @PutMapping("")
    public ResponseEntity<?> updateProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token){
        return null;
    }
}
