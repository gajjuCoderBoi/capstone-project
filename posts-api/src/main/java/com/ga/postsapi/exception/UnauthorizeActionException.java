package com.ga.postsapi.exception;

/*************************************************************************
 *
 *      The UnauthorizedException class extends Exception and
 *      handles the unauthorized exception with an appropriate message
 *
 *************************************************************************/



public class UnauthorizeActionException extends Exception {
    public UnauthorizeActionException(String message) {
        super(message);
    }
}
