package com.ga.usersapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@Import({SpringDataRestConfiguration.class})
@Configuration
public class SwaggerConfig {
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Users API")
                .description(
                        "Users API for User operations. ")
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
                        RequestHandlerSelectors.basePackage("com.ga.usersapi.controller")
                ).build()
                .apiInfo(apiInfo())
                .host("http://localhost:8080/users");


    }

}