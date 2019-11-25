package com.ga.profileapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerDocumentationConfiguration {


	ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Profile API")
				.description(
						"Profile API for Profile operations. ")
				.termsOfServiceUrl("")
				.version("0.0.1-SNAPSHOT")
				.build();
	}

	@Bean
	public Docket configureControllerPackageAndConvertors() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(
						RequestHandlerSelectors.basePackage("com.ga.profileapi.controller")
				).build()
	                .apiInfo(apiInfo())
					.host("http://localhost:8080/profile");


	}




}