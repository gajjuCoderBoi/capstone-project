package com.ga.profileapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**********************************************************
 * The Profileapi manages the profileapi microservice and it is a client of the Eureka
 * Server and APIGateway
 */


@SpringBootApplication
@EnableEurekaClient
public class ProfileAPIApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProfileAPIApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
