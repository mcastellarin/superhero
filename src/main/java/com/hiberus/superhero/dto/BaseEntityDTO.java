package com.hiberus.superhero.dto;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.ReadOnlyProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseEntityDTO implements Serializable {
	private static final long serialVersionUID = -8005484646277043740L;

	@ReadOnlyProperty
	private Long id;

	@ReadOnlyProperty
	private String createdBy;

	@ReadOnlyProperty
	private Instant createdDate;

	@ReadOnlyProperty
	private String lastModifiedBy;

	@ReadOnlyProperty
	private Instant lastModifiedDate;
}
