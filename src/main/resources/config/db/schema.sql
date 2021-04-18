DROP TABLE IF EXISTS superheroes.revision_info;

CREATE TABLE superheroes.revision_info (
    revision_id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    revision_timestamp BIGINT(20) NOT NULL,
    CONSTRAINT PK_REVISION_INFO PRIMARY KEY (revision_id)
);

DROP TABLE IF EXISTS superheroes.superheroes;

CREATE TABLE superheroes.superheroes (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  created_by VARCHAR(255) DEFAULT NULL,
  created_date TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP,
  name VARCHAR(50) NOT NULL UNIQUE,
  name_secret_identity VARCHAR(100) NOT NULL UNIQUE,
  secret_identity VARCHAR(50) NOT NULL,
  origin VARCHAR(50) NOT NULL,
  CONSTRAINT UK_SUPERHERO_NAME UNIQUE KEY (name),
  CONSTRAINT UK_SUPERHERO_NAME_SECRET_IDENTIFY UNIQUE KEY (name_secret_identity),
  CONSTRAINT PK_SUPERHERO PRIMARY KEY (id)
);

DROP TABLE IF EXISTS superheroes.superheroes_h;

CREATE TABLE superheroes.superheroes_h (
  revision_id bigint(20) NOT NULL,
  id bigint(20) NOT NULL,
  revision_type TINYINT NOT NULL,
  created_by VARCHAR(255) DEFAULT NULL,
  created_date TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP,
  name VARCHAR(50),
  name_secret_identity VARCHAR(100),
  secret_identity VARCHAR(50) NOT NULL,
  origin VARCHAR(50) NOT NULL,
  CONSTRAINT PK_SUPERHEROES_H PRIMARY KEY (revision_id, id),
  CONSTRAINT FK_SUPERHEROES_H_REVISION_INFO FOREIGN KEY (revision_id) REFERENCES superheroes.revision_info (revision_id)
);

DROP TABLE IF EXISTS superheroes.superpowers;

CREATE TABLE superheroes.superpowers (
  id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  created_by VARCHAR(255) DEFAULT NULL,
  created_date TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP,
  name VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(250),
  CONSTRAINT UK_SUPERPOWER_NAME UNIQUE KEY (name),
  CONSTRAINT PK_SUPERPOWERS PRIMARY KEY (id)
);

DROP TABLE IF EXISTS superheroes.superpowers_h;

CREATE TABLE superheroes.superpowers_h (
  revision_id bigint(20) NOT NULL,
  id bigint(20) NOT NULL,
  revision_type TINYINT NOT NULL,
  created_by VARCHAR(255) DEFAULT NULL,
  created_date TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_date TIMESTAMP,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(250),
  CONSTRAINT PK_SUPERPOWERS_H PRIMARY KEY (revision_id, id),
  CONSTRAINT FK_SUPERPOWERS_H_REVISION_INFO FOREIGN KEY (revision_id) REFERENCES superheroes.revision_info (revision_id)
);

DROP TABLE IF EXISTS superheroes.superheroes_superpowers;

CREATE TABLE superheroes.superheroes_superpowers (
  superhero_id INT NOT NULL,
  superpower_id INT NOT NULL,
  CONSTRAINT PK_SUPERHEROES_SUPERPOWERS  PRIMARY KEY (superhero_id, superpower_id),
  CONSTRAINT FK_SUPERHEROES_SUPERPOWERS_SUPERHERO_ID FOREIGN KEY (superhero_id) REFERENCES superheroes(id),
  CONSTRAINT FK_SUPERHEROES_SUPERPOWERS_SUPERPOWER_ID FOREIGN KEY (superpower_id) REFERENCES superpowers(id)
);

