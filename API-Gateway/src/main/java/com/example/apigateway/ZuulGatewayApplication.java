package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/********************************************************************************************************
 * The ZuulGateWayApplication class is the APIgateway for this app and takes all API calls from clients, then routes them to the appropriate
 *  microservice with request routing, composition, and protocol translation.
*********************************************************************************************************/
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient

public class ZuulGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulGatewayApplication.class, args);
	}





}
