package com.hiberus.superhero.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@Table(name = "revision_info", schema = "superheroes")
@RevisionEntity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "revision_id")),
		@AttributeOverride(name = "timestamp", column = @Column(name = "revision_timestamp")) })
public class AuditRevisionEntity extends DefaultRevisionEntity {
	private static final long serialVersionUID = -2738378159231789199L;
}
