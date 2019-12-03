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

    /**
     * <p>getUserId() is the getter function for variable userId</p>
     * @return userId type Long
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * <p>setUserId(Long userId) is the setter function for variable userId</p>
     * @param userId type Long
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * <p>getEmail() is the getter function for variable email</p>
     * @return email type String
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>setEmail(Strinbg email) is the setter function for variable email</p>
     * @param email type String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>getUsername() is the getter function for the variable username</p>
     * @return username type String
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>setUsername(String username) is the setter function for the variable username</p>
     * @param username type String
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
