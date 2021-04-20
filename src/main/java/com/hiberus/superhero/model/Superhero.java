package com.hiberus.superhero.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.hiberus.superhero.model.enums.Origin;
import com.hiberus.superhero.model.enums.SecretIdentity;

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
@Table(name = "superheroes", schema = "superheroes")
public class Superhero extends BaseEntity {
	private static final long serialVersionUID = -1162373886607461742L;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "secret_identity", nullable = false)
	private SecretIdentity secretIdentity;

	@Column(name = "name_secret_identity")
	private String nameSecretIdentity;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Origin origin;

	@NotAudited
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "superheroes_superpowers", schema = "superheroes", joinColumns = {
			@JoinColumn(name = "superhero_id") }, inverseJoinColumns = { @JoinColumn(name = "superpower_id") })
	private Set<Superpower> superpowers;

	public Boolean addSuperpower(Superpower superpower) {
		return superpowers.add(superpower);
	}

	public Boolean removeSuperpower(Superpower superpower) {
		return superpowers.remove(superpower);
	}
}
