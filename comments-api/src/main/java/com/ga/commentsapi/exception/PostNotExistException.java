package com.ga.commentsapi.exception;

/*************************************************************************
 *
 *      The PostNotExistException class extends Exception and
 *      handles the post no exist exception with an appropriate message
 *
 *************************************************************************/


public class PostNotExistException extends Exception {
    public PostNotExistException(String message) {
        super(message);
    }
}
