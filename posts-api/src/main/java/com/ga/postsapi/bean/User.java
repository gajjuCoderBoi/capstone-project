package com.ga.postsapi.bean;


import springfox.documentation.annotations.ApiIgnore;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

@ApiIgnore
public class User {


    /*************************************************************************
     *
     *      User bean just to use as an Object.
     *
     *************************************************************************/

    private Long userId;

    private String email;

    private String username;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
