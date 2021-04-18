package com.hiberus.superhero.model.enums;

import org.apache.commons.lang3.StringUtils;

public enum Origin {
	ALIEN("Extraterrestre"), MYTHOLOGICAL_GODS("Dioses mitológicos"), DEMIGODS("Semidioses"), MUTANT("Mutante"),
	DEMONS("Demonios"), ROBOTS("Robots"), OTHERS("Otros");

	private String name;

	private Origin() {
	}

	private Origin(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Origin getByName(String name) {
		for (Origin origin : values()) {
			if (StringUtils.containsAnyIgnoreCase(origin.getName(), name)) {
				return origin;
			}
		}
		return null;
	}
}
