package com.ga.commentsapi.exception;

/*************************************************************************
 *
 *      The TokenException class extends Exception and
 *      handles the missing token exception with an appropriate message
 *
 *************************************************************************/


public class TokenException extends Exception {

    public TokenException(String message) {
        super(message);
    }

}
