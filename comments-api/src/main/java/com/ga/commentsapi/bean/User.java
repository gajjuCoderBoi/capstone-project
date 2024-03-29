package com.ga.commentsapi.bean;

public class User {

    /*************************************************************************
     *
     *      User model just to use as an Object.
     *      Here it's not and Entity.
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