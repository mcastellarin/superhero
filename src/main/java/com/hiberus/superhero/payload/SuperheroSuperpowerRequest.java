package com.hiberus.superhero.payload;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuperheroSuperpowerRequest implements Serializable {
	private static final long serialVersionUID = -3454756803545441698L;

	@NotNull
	private Long superheroId;

	@NotNull
	private Long superpowerId;
}
