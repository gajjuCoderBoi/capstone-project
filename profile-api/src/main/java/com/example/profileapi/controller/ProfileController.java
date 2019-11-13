package com.example.profileapi.controller;

import com.example.profileapi.model.Profile;
import com.example.profileapi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping
public class ProfileController {


    /*************************************************************************
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/

    @Autowired
    ProfileService profileService;


    /*************************************************************************
     *       todo
     *      Comment for Getmapping
     *
     *************************************************************************/

    @GetMapping
    public ResponseEntity<?> getProfile(){
        return null;
    }


    /*************************************************************************
     *       todo
     *      Comment for Postmapping
     *
     *************************************************************************/

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(profileService.createProfile(profile, token));
    }

    /*************************************************************************
     *       todo
     *      Comment for PutMapping
     *
     *************************************************************************/
    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token){
        return null;
    }
}
