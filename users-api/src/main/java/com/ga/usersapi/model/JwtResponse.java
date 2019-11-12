package com.ga.usersapi.model;

import com.ga.usersapi.config.JwtUtil;

import java.util.List;

public class JwtResponse {

    private String jwt;

    private String username;

    public JwtResponse(String jwt) {
        this.jwt = JwtUtil.extractToken(jwt);
    }

    public JwtResponse(List<String> signup) {
        username = signup.get(0);
        jwt = JwtUtil.extractToken(signup.get(1));
    }

    public String getToken() {
        return this.jwt;
    }

    public String getUsername() {
        return username;
    }

}
