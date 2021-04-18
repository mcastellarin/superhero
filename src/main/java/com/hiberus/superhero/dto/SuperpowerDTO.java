package com.hiberus.superhero.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class SuperpowerDTO extends BaseEntityDTO {
	private static final long serialVersionUID = -3104282623488822784L;

	@NotBlank
	@Size(min = 3, max = 50)
	private String name;

	@Null
	@Size(max = 250)
	private String description;

	public SuperpowerDTO() {
		super();
	}

	private SuperpowerDTO(Builder builder) {
		name = builder.name;
		description = builder.description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Creates builder to build {@link SuperpowerDTO}.
	 *
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link SuperpowerDTO}.
	 */
	public static final class Builder {
		private String name;
		private String description;

		private Builder() {
		}

		public Builder withName(String newName) {
			name = newName;
			return this;
		}

		public Builder withDescription(String newDescription) {
			description = newDescription;
			return this;
		}

		public SuperpowerDTO build() {
			return new SuperpowerDTO(this);
		}
	}
}
