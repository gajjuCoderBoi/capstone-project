package com.ga.usersapi.controller;


import com.ga.usersapi.exception.LoginException;
import com.ga.usersapi.exception.UserAlreadyExistException;
import com.ga.usersapi.model.JwtResponse;
import com.ga.usersapi.model.User;
import com.ga.usersapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
@Api(tags = "User Management System", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;


// TODO
    // Re-Work on Swagger API.
    @ApiOperation(value = "Sign Up a new user", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully signed up user")
    })
    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody User user) throws UserAlreadyExistException {
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }


    @ApiOperation(value = "login a user", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully user login")
    })
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody User user) throws LoginException {
        return ResponseEntity.ok(new JwtResponse(userService.login(user)));
    }


    @ApiOperation(value = "Return a User ", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved a user")
    })
    @GetMapping
    public ResponseEntity getUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.getUserByToken(token));
    }



}
