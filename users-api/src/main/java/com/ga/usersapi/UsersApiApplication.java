package com.ga.usersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class UsersApiApplication {

    @RequestMapping("/")
    public String home() {
        return "some users";
    }

	public static void main(String[] args) {
		SpringApplication.run(UsersApiApplication.class, args);
	}

}
