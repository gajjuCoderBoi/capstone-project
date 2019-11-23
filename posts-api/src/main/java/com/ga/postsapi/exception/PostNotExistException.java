package com.ga.postsapi.exception;

public class PostNotExistException extends Exception {
    public PostNotExistException(String message) {
        super(message);
    }
}
