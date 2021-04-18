package com.hiberus.superhero.dto;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.ReadOnlyProperty;

public class BaseEntityDTO implements Serializable {
	private static final long serialVersionUID = -8005484646277043740L;

	@ReadOnlyProperty
	private Long id;

	@ReadOnlyProperty
	private String createdBy;

	@ReadOnlyProperty
	private Instant createdDate = Instant.now();

	private String lastModifiedBy;

	private Instant lastModifiedDate = Instant.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
