package com.hiberus.superhero.dto;

import java.util.Collections;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.ReadOnlyProperty;

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

	private SuperheroDTO(Builder builder) {
		name = builder.name;
		secretIdentity = builder.secretIdentity;
		nameSecretIdentity = builder.nameSecretIdentity;
		origin = builder.origin;
		superpowers = builder.superpowers;
	}

	public SuperheroDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameSecretIdentity() {
		return nameSecretIdentity;
	}

	public void setNameSecretIdentity(String nameSecretIdentity) {
		this.nameSecretIdentity = nameSecretIdentity;
	}

	public String getSecretIdentity() {
		return secretIdentity;
	}

	public void setSecretIdentity(String secretIdentity) {
		this.secretIdentity = secretIdentity;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Set<SuperpowerDTO> getSuperpowers() {
		return superpowers;
	}

	public void setSuperpowers(Set<SuperpowerDTO> superpowers) {
		this.superpowers = superpowers;
	}

	/**
	 * Creates builder to build {@link SuperheroDTO}.
	 *
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link SuperheroDTO}.
	 */
	public static final class Builder {
		private String name;
		private String secretIdentity;
		private String nameSecretIdentity;
		private String origin;
		private Set<SuperpowerDTO> superpowers = Collections.emptySet();

		private Builder() {
		}

		public Builder withName(String newName) {
			name = newName;
			return this;
		}

		public Builder withSecretIdentity(String newSecretIdentity) {
			secretIdentity = newSecretIdentity;
			return this;
		}

		public Builder withNameSecretIdentity(String newNameSecretIdentity) {
			nameSecretIdentity = newNameSecretIdentity;
			return this;
		}

		public Builder withOrigin(String newOrigin) {
			origin = newOrigin;
			return this;
		}

		public Builder withSuperpowers(Set<SuperpowerDTO> newSuperpowers) {
			superpowers = newSuperpowers;
			return this;
		}

		public SuperheroDTO build() {
			return new SuperheroDTO(this);
		}
	}
}
