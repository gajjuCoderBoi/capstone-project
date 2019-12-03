package com.ga.usersapi.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class MyExceptionHandlerTest {

    @InjectMocks
    MyExceptionHandler myExceptionHandler;

    @InjectMocks
    ErrorResponse dummyErrorResponse;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String timestamp = LocalDateTime.now().format(formatter);


    @Test
    public void handleLoginException() {
        dummyErrorResponse.setMessage(Arrays.asList("Invalid Username and Password."));
        dummyErrorResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        dummyErrorResponse.setTimestamp(timestamp);

        ResponseEntity<ErrorResponse> actual = myExceptionHandler.handleLoginException(new LoginException("test"));

        assertEquals(actual.getStatusCode(), dummyErrorResponse.getHttpStatus());
    }

    @Test
    public void handleUserAlreadyExistException() {
        dummyErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        dummyErrorResponse.setTimestamp(timestamp);

        ResponseEntity<ErrorResponse> actual = myExceptionHandler.handleUserAlreadyExistException(new UserAlreadyExistException("test"));

        assertEquals(actual.getStatusCode(), dummyErrorResponse.getHttpStatus());
    }

}