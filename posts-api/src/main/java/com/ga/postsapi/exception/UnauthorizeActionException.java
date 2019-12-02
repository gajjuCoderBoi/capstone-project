package com.ga.postsapi.exception;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/
/**
 *
 *     <p>The UnauthorizedException class extends Exception and
 *      handles the unauthorized exception with an appropriate message
 *</p>
 *************************************************************************/



public class UnauthorizeActionException extends Exception {
    public UnauthorizeActionException(String message) {
        super(message);
    }
}
