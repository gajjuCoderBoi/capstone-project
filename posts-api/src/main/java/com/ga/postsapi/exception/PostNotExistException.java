package com.ga.postsapi.exception;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

/**
 *
 *    <p>The PostNotExistException class extends Exception and
 *      handles the post no exist exception with an appropriate message</p>
 *
 *************************************************************************/

public class PostNotExistException extends Exception {
    public PostNotExistException(String message) {
        super(message);
    }
}
