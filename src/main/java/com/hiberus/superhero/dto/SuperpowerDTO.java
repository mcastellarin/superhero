package com.hiberus.superhero.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuperpowerDTO extends BaseEntityDTO {
	private static final long serialVersionUID = -3104282623488822784L;

	@NotBlank
	@Size(min = 3, max = 50)
	private String name;

	@Null
	@Size(max = 250)
	private String description;
}
