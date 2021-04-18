package com.hiberus.superhero.config;

import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CustomObjectMapperConfig {

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
