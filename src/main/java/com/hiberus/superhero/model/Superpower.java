package com.hiberus.superhero.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Audited
@AuditOverride(forClass = BaseEntity.class, isAudited = true)
@Table(name = "superpowers", schema = "superheroes")
public class Superpower extends BaseEntity {
	private static final long serialVersionUID = -1241680635536537821L;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@ManyToMany(mappedBy = "superpowers")
	@JsonBackReference
	Set<Superhero> superheros;

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

	public Set<Superhero> getSuperheros() {
		return superheros;
	}

	public void setSuperheros(Set<Superhero> superheros) {
		this.superheros = superheros;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return Boolean.TRUE;
		}
		if (Objects.isNull(obj) || !getClass().equals(obj.getClass())) {
			return Boolean.FALSE;
		}
		Superpower superpower = (Superpower) obj;
		if (Objects.isNull(superpower.getId()) || Objects.isNull(getId())) {
			return Boolean.FALSE;
		}
		return Objects.equals(getId(), superpower.getId());
	}

	@Override
	public String toString() {
		return "SuperpowerDTO [name=" + getName() + ", description=" + getDescription() + "]";
	}
}
