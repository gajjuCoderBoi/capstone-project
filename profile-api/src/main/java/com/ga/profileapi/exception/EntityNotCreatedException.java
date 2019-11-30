package com.ga.profileapi.exception;

/*************************************************************************
 *
 *      The EntityNotCreatedException class extends Exception and
 *      handles the post no exist exception with an appropriate message
 *
 *************************************************************************/

public class EntityNotCreatedException extends Exception {

    public EntityNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
