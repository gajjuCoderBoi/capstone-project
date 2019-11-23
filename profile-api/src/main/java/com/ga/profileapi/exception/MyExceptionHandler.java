package com.ga.profileapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
   @ExceptionHandler(ProfileNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleProfileException(ProfileNotFoundException e){
       List<String> details = new ArrayList<>();
       details.add("Profile does not exist.");
       String causeMessage = (e.getCause() == null) ? "" : e.getMessage();
       ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, details, causeMessage);
       return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
   }



    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException e){
        List<String> details = new ArrayList<>();
        details.add("Invalid Token.");
        String causeMessage = (e.getCause() == null) ? "" : e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, details, causeMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
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