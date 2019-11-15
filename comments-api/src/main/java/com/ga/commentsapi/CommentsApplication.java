package com.ga.commentsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CommentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentsApplication.class, args);
	}

	/*************************************************************************
	 *
	 *	Creating Bean of RestTemplate.
	 *
	 *************************************************************************/

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
