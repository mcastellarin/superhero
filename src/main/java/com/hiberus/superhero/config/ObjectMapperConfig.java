package com.hiberus.superhero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ComponentScan
@Configuration
public class ObjectMapperConfig {
	
	@Bean(name = "objectMapperForSuperhero")
	public ObjectMapper objectMapper() {

		ObjectMapper maper = new ObjectMapper()
				.setSerializationInclusion(Include.NON_NULL)
				.registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
				.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return maper;
	}
}
