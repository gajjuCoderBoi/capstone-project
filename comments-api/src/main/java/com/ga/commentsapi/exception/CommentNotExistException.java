package com.ga.commentsapi.exception;

/*************************************************************************
 *
 *      The CommentNotExistException class extends Exception and
 *      handles the comment no exist exception with an appropriate message
 *
 *************************************************************************/


public class CommentNotExistException extends Exception {

    public CommentNotExistException(String message) {
        super(message);
    }

}
