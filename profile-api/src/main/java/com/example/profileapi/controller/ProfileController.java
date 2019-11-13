package com.example.profileapi.controller;

import com.example.profileapi.model.Profile;
import com.example.profileapi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class ProfileController {

    @GetMapping("/test")
    public String test(){
        return "Test Profile";
    }


    /*************************************************************************
     *
     *      Autowiring the profileService that will be used to get the data
     *      from the service layer.
     *
     *************************************************************************/

    @Autowired
    ProfileService profileService;


    /*************************************************************************
     *
     *      getProfile is responsible to response the profile of a User.
     *
     *      getProfile is a GET-Request Handler that receives the Bearer-token
     *      and send to the Service layer and return the response according
     *      to Service layer response.
     *
     *************************************************************************/

    @GetMapping
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token){
        Profile savedProfile = profileService.getProfile(token);
        System.out.println(savedProfile);
        return savedProfile!=null ?
                ResponseEntity.ok(savedProfile) :
                new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);

    }


    /*************************************************************************
     *
     *      createProfile is responsible to create the profile of a User.
     *
     *      createProfile is a POST-Request Handler that receives the Bearer-token
     *      and Profile data from front-end and send to the Service layer and
     *      return the response according to Service layer response.
     *
     *************************************************************************/

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token){
        Profile savedProfile = profileService.createProfile(profile, token);
        return savedProfile!=null ?
                ResponseEntity.ok(savedProfile) :
                new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    /*************************************************************************
     *
     *      updateProfile is responsible to update the profile of a User.
     *
     *      updateProfile is a PUT-Request Handler that receives the Bearer-token
     *      and Profile data from front-end and send to the Service layer and
     *      return the response according to Service layer response.
     *
     *************************************************************************/
    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token){
        Profile savedProfile = profileService.updateProfile(profile, token);
        return savedProfile!=null ?
                ResponseEntity.ok(savedProfile) :
                new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
