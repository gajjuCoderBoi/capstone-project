package com.ga.usersapi.exception;

public class EntityNotFoundException extends Exception {

    /*************************************************************************
     *
     *      The EntityNotFoundException class extends Exception and
     *      handles the entity not found exception with an appropriate message
     *
     *************************************************************************/


    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
