package com.ga.usersapi.exception;


/*************************************************************************
 *
 *      The UserAlreadyExistException class extends Exception and
 *      handles the user already exists exception with an appropriate message
 *
 *************************************************************************/

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
