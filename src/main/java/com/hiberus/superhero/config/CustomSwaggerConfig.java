package com.hiberus.superhero.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class CustomSwaggerConfig {

	@Bean
	public Docket superheroApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.hiberus.superhero.controller"))
				.paths(regex("/api/.*"))
				.build().apiInfo(getApiInfo());
	}

	private static ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("Superhero REST API")
				.description("Hiberus technical project")
				.version("1.0.0").contact(new Contact("Mailen Castellarin", "https://ar.linkedin.com/in/mcastellarin20", "mailencastellarin@gmail.com"))
				.build();
	}
}
