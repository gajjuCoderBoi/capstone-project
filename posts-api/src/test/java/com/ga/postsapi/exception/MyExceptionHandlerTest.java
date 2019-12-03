package com.ga.postsapi.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class MyExceptionHandlerTest {
    @InjectMocks
    MyExceptionHandler myExceptionHandler;

    @InjectMocks
    ErrorResponse dummyErrorResponse;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String timestamp = LocalDateTime.now().format(formatter);


    @Test
    public void handleUnauthorizedActionException() {
        dummyErrorResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        dummyErrorResponse.setTimestamp(timestamp);

        ResponseEntity<ErrorResponse> actual = myExceptionHandler.handleUnauthorizedActionException(new UnauthorizeActionException("test"));

        assertEquals(actual.getStatusCode(), dummyErrorResponse.getHttpStatus());
    }

    @Test
    public void handleTokenException() {
        dummyErrorResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        dummyErrorResponse.setTimestamp(timestamp);

        ResponseEntity<ErrorResponse> actual = myExceptionHandler.handleTokenException(new TokenException("test"));

        assertEquals(actual.getStatusCode(), dummyErrorResponse.getHttpStatus());
    }

    @Test
    public void handlePostNotExistException() {
        dummyErrorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        dummyErrorResponse.setTimestamp(timestamp);

        ResponseEntity<ErrorResponse> actual = myExceptionHandler.handlePostNotExistException(new PostNotExistException("test"));

        assertEquals(actual.getStatusCode(), dummyErrorResponse.getHttpStatus());
    }
}