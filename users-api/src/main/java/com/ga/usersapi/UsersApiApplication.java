package com.ga.usersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/****************************************************************************
 * The UsersApiApplication class is the main class in the usersapi. It is
 * an EurekaClient
 */
@SpringBootApplication
@EnableEurekaClient
public class UsersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApiApplication.class, args);
	}

}
