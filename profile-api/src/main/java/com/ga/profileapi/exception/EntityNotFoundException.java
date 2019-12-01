package com.ga.profileapi.exception;


/*************************************************************************
 *
 *      The EntityNotFoundException class extends Exception and
 *      handles the entity not found with an appropriate message
 *
 *************************************************************************/

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
