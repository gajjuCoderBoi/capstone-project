package com.ga.postsapi.bean;


public class User {

    /*************************************************************************
     *
     *      User bean just to use as an Object.
     *
     *************************************************************************/

    private Long userId;

    private String email;

//    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getusername() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
