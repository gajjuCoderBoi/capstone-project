package com.ga.postsapi.model;


public class User {

    /*************************************************************************
     *
     *      User model just to use as an Object.
     *      Here it's not and Entity.
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
