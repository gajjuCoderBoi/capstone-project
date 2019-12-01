package com.ga.commentsapi.exception;

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
