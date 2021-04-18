package com.hiberus.superhero.exceptions.response;

import org.springframework.http.ResponseEntity;

import com.hiberus.superhero.exceptions.error.CustomErrorResponse;

public class ResponseEntityBuilder {

	public static ResponseEntity<Object> build(CustomErrorResponse customErrorResponse) {
		return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
	}
}
