package com.ga.profileapi.exception;

/*************************************************************************
 *
 *      The ProfileNotFoundException class extends Exception and
 *      handles the profile no found exception with an appropriate message
 *
 *************************************************************************/

public class ProfileNotFoundException extends Exception {
    public ProfileNotFoundException(String message){
        super(message);
    };
}
