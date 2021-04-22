package com.hiberus.superhero.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.ReadOnlyProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuperheroDTO extends BaseEntityDTO {
	private static final long serialVersionUID = 5452111326565875622L;

	@NotBlank
	@Size(min = 3, max = 50)
	private String name;

	@NotNull
	@Size(min = 3, max = 50)
	private String secretIdentity;

	@Size(max = 100)
	private String nameSecretIdentity;

	@NotNull
	@Size(min = 3, max = 50)
	private String origin;

	@ReadOnlyProperty
	private Set<SuperpowerDTO> superpowers;
}
