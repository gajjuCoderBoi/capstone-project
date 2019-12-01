package com.ga.commentsapi.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/******************************************************************************************
 *
 *      The ErrorResponse has the following variables: httpStatus, the message, the cause and a timestamp.
 *      It has several constructors and it has four getter methods:
 *
 *      1. getHttpStatus()
 *      2. getMessage()
 *      3. getCause()
 *      4. getTimestamp()
 *
 ******************************************************************************************/

public class ErrorResponse {

    private HttpStatus httpStatus;
    private List<String> message;
    private String cause;
    private String timestamp;

    public ErrorResponse(HttpStatus httpStatus, List<String> message, String cause) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
        this.cause = cause;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public ErrorResponse(HttpStatus httpStatus, List<String> message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<String> getMessage() {
        return message;
    }

    public String getCause() {
        return cause;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
