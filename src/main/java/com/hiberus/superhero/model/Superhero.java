package com.hiberus.superhero.model;

import java.util.Objects;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SecretIdentity getSecretIdentity() {
		return secretIdentity;
	}

	public void setSecretIdentity(SecretIdentity secretIdentity) {
		this.secretIdentity = secretIdentity;
	}

	public String getNameSecretIdentity() {
		return nameSecretIdentity;
	}

	public void setNameSecretIdentity(String nameSecretIdentity) {
		this.nameSecretIdentity = nameSecretIdentity;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Set<Superpower> getSuperpowers() {
		return superpowers;
	}

	public void setSuperpowers(Set<Superpower> superpowers) {
		this.superpowers = superpowers;
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
		Superhero superhero = (Superhero) obj;
		if (Objects.isNull(superhero.getId()) || Objects.isNull(getId())) {
			return Boolean.FALSE;
		}
		return Objects.equals(getId(), superhero.getId());
	}

	@Override
	public String toString() {
		return "Superhero [name=" + getName() + ", secretIdentity=" + getSecretIdentity() + ", nameSecretIdentity="
				+ getNameSecretIdentity() + ", origin=" + getOrigin() + ", superpowers=" + getSuperpowers() + "]";
	}

	public Boolean addSuperpower(Superpower superpower) {
		return getSuperpowers().add(superpower);
	}

	public Boolean removeSuperpower(Superpower superpower) {
		return getSuperpowers().remove(superpower);
	}
}
