package com.ga.usersapi.exception;

public class EntityNotCreatedException extends Exception {

    /*************************************************************************
     *
     *      The EntityNotCreatedException class extends Exception and
     *      handles the user not created exception with an appropriate message
     *
     *************************************************************************/

    public EntityNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
