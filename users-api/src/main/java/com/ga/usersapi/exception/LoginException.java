package com.ga.usersapi.exception;


/*************************************************************************
 *
 *      The LoginException class extends Exception and
 *      handles the login exception with an appropriate message
 *
 *************************************************************************/


public class LoginException extends Exception {

    public LoginException(String message) {
        super(message);
    }

}
