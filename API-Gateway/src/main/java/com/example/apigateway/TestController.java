package com.example.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/********************************************************************************************************
 * The TestController is just a test class for the APIGateway
 *********************************************************************************************************/
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
