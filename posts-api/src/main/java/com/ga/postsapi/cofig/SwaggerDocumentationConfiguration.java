package com.ga.postsapi.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author      Mohammad Javed and Carlos Kruger
 ************************************************************/

/**
*     The SwaggerDocumentationConfiguration class enables Swagger.
*     It declares the base Package as well as the base url where the endpoints
*     will be viewed by the user*
*************************************************************************/


@Configuration
@EnableSwagger2WebMvc
public class SwaggerDocumentationConfiguration {

	/**
	 * <p>apiInfo() builds Swagger postapi</p>
	 * @return ApiInfo
	 */
	ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Posts API")
				.description(
						"Post API for Post operations. ")
				.termsOfServiceUrl("")
				.version("0.0.1-SNAPSHOT")
				.build();
	}

	/**
	 * <p>configureControllerPackageAndConvertors() configures the controller, defining the package and the endpoints for this app to work with Swagger</p>
	 * @return Docket
	 */
	@Bean
	public Docket configureControllerPackageAndConvertors() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(
						RequestHandlerSelectors.basePackage("com.ga.postsapi.controller")
				).build()
	                .apiInfo(apiInfo())
					.host("http://localhost:8080/posts");


	}




}
