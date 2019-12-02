package com.ga.postsapi.exception;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

/**
 *    <p>The TokenException class extends Exception and
 *      handles the missing token exception with an appropriate message
 *    </p>
 *************************************************************************/

public class TokenException extends Exception {

    public TokenException(String message) {
        super(message);
    }

}
