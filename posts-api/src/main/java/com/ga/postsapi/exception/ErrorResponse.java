package com.ga.postsapi.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

/**
*
*    <p> The ErrorResponse class has the following variables: httpStatus, the message, the cause and a timestamp.
*      It has several constructors and it has four getter methods:
*
*      1. getHttpStatus()
*      2. getMessage()
*      3. getCause()
*      4. getTimestamp()
*</P
******************************************************************************************/


public class ErrorResponse {

    private HttpStatus httpStatus;
    private List<String> message;
    private String cause;
    private String timestamp;

    /**
     * <p> Constructor for ErrorResponse class</p>
     * @param httpStatus
     * @param message
     * @param cause
     *
     */

    public ErrorResponse(HttpStatus httpStatus, List<String> message, String cause) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
        this.cause = cause;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    /**
     * <p>Constructor for ErrorResponse class</p>
     * @param httpStatus
     * @param message
     */

    public ErrorResponse(HttpStatus httpStatus, List<String> message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    /**
     * <p>Getter for HttpStatus</p>
     * @return httpStatus type httpStatus
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * <p>Getter for message</p>
     * @return message type String
     */
    public List<String> getMessage() {
        return message;
    }

    /**
     * <p>Getter for Cause</p>
     * @return cause type String
     */

    public String getCause() {
        return cause;
    }

    /**
     * <p>Getter for timestamp</p>
     * @return timestamp type String
     */

    public String getTimestamp() {
        return timestamp;
    }
}
