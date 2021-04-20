package com.hiberus.superhero.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
