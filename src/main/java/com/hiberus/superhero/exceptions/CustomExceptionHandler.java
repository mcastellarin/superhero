package com.hiberus.superhero.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hiberus.superhero.exceptions.error.CustomErrorResponse;
import com.hiberus.superhero.exceptions.error.constant.CustomExceptionErrorMessageConstant;
import com.hiberus.superhero.exceptions.response.ResponseEntityBuilder;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<String>();
		StringBuilder builder = new StringBuilder()
				.append(e.getContentType())
				.append(StringUtils.SPACE)
				.append(CustomExceptionErrorMessageConstant.ERROR_MESSAGE_INVALID_JSON_DETAILS)
				.append(StringUtils.SPACE);

		e.getSupportedMediaTypes().forEach(smt -> builder.append(smt).append(", "));
		details.add(builder.toString());

		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_INVALID_JSON, details);
		return ResponseEntityBuilder.build(error);
	}

	// triggers when the JSON is malformed
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_MALFORMED_JSON_REQUEST, Arrays.asList(e.getMessage()));

		return ResponseEntityBuilder.build(error);
	}

	// triggers when @Valid fails
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		if(Objects.nonNull(e.getBindingResult()) && CollectionUtils.isNotEmpty(e.getBindingResult().getFieldErrors())) {
			details = e.getBindingResult().getFieldErrors().stream()
					.map(fieldError -> fieldError.getObjectName() + StringUtils.SPACE + ":" + StringUtils.SPACE + fieldError.getDefaultMessage())
					.collect(Collectors.toList());
		}

		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_VALIDATION_ERRORS, details);

		return ResponseEntityBuilder.build(error);
	}

	// triggers when there are missing parameters
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = Arrays.asList(e.getParameterName() + StringUtils.SPACE + "parameter is missing");

		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_MISSING_PARAMETERS, details);

		return ResponseEntityBuilder.build(error);
	}

	// triggers when a parameter's type does not match
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, WebRequest request) {
		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_MISMATCH_TYPE, Arrays.asList(e.getMessage()));

		return ResponseEntityBuilder.build(error);
	}

	// triggers when @Validated fails
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(Exception e, WebRequest request) {
		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_CONSTRAINT_VIOLATION, Arrays.asList(e.getMessage()));

		return ResponseEntityBuilder.build(error);
	}

	// triggers when there is not resource with the specified ID in BDD
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.NOT_FOUND,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_RESOURCE_NOT_FOUND, Arrays.asList(e.getMessage()));

		return ResponseEntityBuilder.build(error);
	}

	// triggers when the handler method is invalid
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_METHOD_NOT_FOUND, details);

		return ResponseEntityBuilder.build(error);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		CustomErrorResponse error = new CustomErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST,
				CustomExceptionErrorMessageConstant.ERROR_MESSAGE_GENERIC, Arrays.asList(ex.getLocalizedMessage()));

		return ResponseEntityBuilder.build(error);
	}
}
