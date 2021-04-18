package com.hiberus.superhero.payload;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class SuperheroSuperpowerRequest implements Serializable {
	private static final long serialVersionUID = -3454756803545441698L;

	@NotNull
	private Long superheroId;

	@NotNull
	private Long superpowerId;

	public Long getSuperheroId() {
		return superheroId;
	}

	public void setSuperheroId(Long superheroId) {
		this.superheroId = superheroId;
	}

	public Long getSuperpowerId() {
		return superpowerId;
	}

	public void setSuperpowerId(Long superpowerId) {
		this.superpowerId = superpowerId;
	}
}
