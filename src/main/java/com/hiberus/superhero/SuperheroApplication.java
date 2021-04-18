package com.hiberus.superhero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hiberus.superhero.config.CustomCacheConfig;
import com.hiberus.superhero.config.CustomObjectMapperConfig;
import com.hiberus.superhero.config.CustomSpringAspectConfig;
import com.hiberus.superhero.config.CustomSwaggerConfig;

@Configuration
@ComponentScan(basePackageClasses = { SuperheroApplication.class })
@Import({CustomObjectMapperConfig.class, CustomSwaggerConfig.class, CustomCacheConfig.class, CustomSpringAspectConfig.class})
@SpringBootApplication
public class SuperheroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperheroApplication.class, args);
	}

}
