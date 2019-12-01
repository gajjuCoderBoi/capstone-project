package com.ga.usersapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*************************************************************************
 *
 *      The MyExceptionHandler class extends ResponseEntityExceptionHandler class and
 *      handles the different exceptions. It is here where the message details and status are built
 *      for the http response
 *
 *************************************************************************/

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException e){
        List<String> details = new ArrayList<>();
        details.add("Invalid Username and Password.");
        String causeMessage = (e.getCause() == null) ? "" : e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, details, causeMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException e){
        List<String> details = Arrays.asList(e.getMessage());
        String causeMessage = (e.getCause() == null) ? "" : e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, details, causeMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, details);
        System.out.println("!!!!!! Invalid field !!!!!!");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}