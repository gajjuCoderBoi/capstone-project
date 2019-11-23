package com.ga.profileapi.controller;

import com.ga.profileapi.exception.ProfileNotFoundException;
import com.ga.profileapi.exception.TokenException;
import com.ga.profileapi.model.Profile;
import com.ga.profileapi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class ProfileController {

    @GetMapping("/test")
    public String test() {
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
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) throws ProfileNotFoundException, TokenException {
        Profile savedProfile = profileService.getProfile(token);
        System.out.println(savedProfile);
        return ResponseEntity.ok(savedProfile) ;

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
    public ResponseEntity<?> createProfile(@Valid @RequestBody Profile profile, @RequestHeader("Authorization") String token) throws ProfileNotFoundException, TokenException {
        Profile savedProfile = profileService.createProfile(profile, token);
        return ResponseEntity.ok(savedProfile);
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
    public ResponseEntity<?> updateProfile(@Valid @RequestBody Profile profile, @RequestHeader("Authorization") String token) throws ProfileNotFoundException, TokenException {
        Profile savedProfile = profileService.updateProfile(profile, token);
        return ResponseEntity.ok(savedProfile);
    }
}
