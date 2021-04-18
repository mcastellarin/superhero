package com.hiberus.superhero.exceptions.error;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomErrorResponse {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
	private Instant timestamp;
	private HttpStatus status;
	private String message;
	private List<String> errors;

	public CustomErrorResponse() {
		super();
	}

	private CustomErrorResponse(Builder builder) {
		timestamp = builder.timestamp;
		status = builder.status;
		message = builder.message;
		errors = builder.errors;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	/**
	 * Creates builder to build {@link CustomErrorResponse}.
	 *
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link CustomErrorResponse}.
	 */
	public static final class Builder {
		private Instant timestamp;
		private HttpStatus status;
		private String message;
		private List<String> errors = Collections.emptyList();

		private Builder() {
		}

		public Builder withTimestamp(Instant newTimestamp) {
			timestamp = newTimestamp;
			return this;
		}

		public Builder withStatus(HttpStatus newStatus) {
			status = newStatus;
			return this;
		}

		public Builder withMessage(String newMessage) {
			message = newMessage;
			return this;
		}

		public Builder withErrors(List<String> newErrors) {
			errors = newErrors;
			return this;
		}

		public CustomErrorResponse build() {
			return new CustomErrorResponse(this);
		}
	}
}
