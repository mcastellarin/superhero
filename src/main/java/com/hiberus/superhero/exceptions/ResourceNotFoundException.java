package com.hiberus.superhero.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 3573224564695212242L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String entityName, String nameParameter, String valueParameter) {
		super(new StringBuilder()
				.append(entityName)
				.append(StringUtils.SPACE)
				.append("with ")
				.append(nameParameter)
				.append(": ")
				.append(valueParameter)
				.append(StringUtils.SPACE)
				.append("not found.").toString());
	}

	public ResourceNotFoundException(String entityName1, String entityName2, String nameParameter, String valueParam1, String valueParam2) {
		super(new StringBuilder()
				.append("Not exists ")
				.append(entityName1)
				.append(StringUtils.SPACE)
				.append("with ")
				.append(nameParameter)
				.append(": ")
				.append(valueParam1)
				.append(StringUtils.SPACE)
				.append("o ")
				.append(entityName2)
				.append(StringUtils.SPACE)
				.append("with ")
				.append(nameParameter)
				.append(": ")
				.append(valueParam2)
				.toString());
	}
}
