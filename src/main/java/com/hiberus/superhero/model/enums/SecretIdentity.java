package com.hiberus.superhero.model.enums;

import org.apache.commons.lang3.StringUtils;

import com.hiberus.superhero.model.enums.SecretIdentity;

public enum SecretIdentity {

	CIVIL_IDENTITY("Identidad Civil"),
	SUPERHERO_IDENTITY("Identidad Super Heroe");

	private String name;

	private SecretIdentity() {}

	private SecretIdentity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static SecretIdentity getByName(String name) {
		for (SecretIdentity secretIdentity : values()) {
			if (StringUtils.containsAnyIgnoreCase(secretIdentity.getName(), name)) {
				return secretIdentity;
			}
		}
		return null;
	}
}
