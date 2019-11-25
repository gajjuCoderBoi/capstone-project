package com.ga.profileapi.controller;

import com.ga.profileapi.exception.ProfileNotFoundException;
import com.ga.profileapi.exception.TokenException;
import com.ga.profileapi.model.Profile;
import com.ga.profileapi.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@Api(tags = "Profile Management System", produces = "application/json")
public class ProfileController {

    @ApiOperation(value = "Test for the profile controller", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Test Profile")
    })
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

    @ApiOperation(value = "Retrieves a user profile", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved  Profile")
    })
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


    @ApiOperation(value = "Creates a user profile", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved  Profile")
    })
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
