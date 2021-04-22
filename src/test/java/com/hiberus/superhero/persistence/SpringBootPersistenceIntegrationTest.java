package com.hiberus.superhero.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hiberus.superhero.model.Superhero;
import com.hiberus.superhero.model.Superpower;
import com.hiberus.superhero.model.enums.Origin;
import com.hiberus.superhero.model.enums.SecretIdentity;
import com.hiberus.superhero.repository.SuperheroRepository;
import com.hiberus.superhero.repository.SuperpowerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPersistenceIntegrationTest {

	@Autowired
	private SuperheroRepository superheroRepository;

	@Autowired
	private SuperpowerRepository superpowerRepository;

	@Test
	public void saveSuperheroEntity() {
		Superhero superheroNew = new Superhero("WONDER WOMAN", SecretIdentity.CIVIL_IDENTITY, "DIANA PRINCE",
				Origin.MYTHOLOGICAL_GODS, null);
		superheroNew.setCreatedDate(Instant.now());
		superheroNew.setCreatedBy("");

		superheroNew = superheroRepository.save(superheroNew);
		assertNotNull(superheroNew);
		assertThat(superheroNew.getId()).isNotEqualTo(null);
	}

	@Test
	public void saveSuperpowerEntity() {
		Superpower superpowerNew = new Superpower("INMORTALITY", StringUtils.EMPTY, null);
		superpowerNew.setCreatedDate(Instant.now());
		superpowerNew.setCreatedBy("admin");

		superpowerNew = superpowerRepository.save(superpowerNew);
		assertNotNull(superpowerNew);
		assertThat(superpowerNew.getId()).isNotEqualTo(null);
	}

	@Test
	public void addSuperpowersToSuperhero() {
		List<Superpower> superpowers = new ArrayList<>();

		Superpower sp1New = new Superpower("REFLEXES", StringUtils.EMPTY, null);
		sp1New.setCreatedDate(Instant.now());
		sp1New.setCreatedBy("admin");
		superpowers.add(sp1New);

		Superpower sp2New = new Superpower("SUPER VELOCITY", "Is a superpower that allows a person to move faster than humanly possible", null);
		sp2New.setCreatedDate(Instant.now());
		sp2New.setCreatedBy("admin");
		superpowers.add(sp2New);

		superpowers = superpowerRepository.saveAll(superpowers);
		assertNotNull(superpowers);
		assertFalse(CollectionUtils.isEmpty(superpowers));

		Superhero superheroNew = new Superhero("WONDER WOMAN", SecretIdentity.CIVIL_IDENTITY, "DIANA PRINCE",
				Origin.MYTHOLOGICAL_GODS, null);
		superheroNew.setCreatedDate(Instant.now());
		superheroNew.setCreatedBy("admin");

		superheroNew = superheroRepository.save(superheroNew);
		assertNotNull(superheroNew);
		assertThat(superheroNew.getId()).isNotEqualTo(null);

		superheroNew.setSuperpowers(new HashSet<Superpower>(superpowers));
		superheroNew = superheroRepository.save(superheroNew);
		assertFalse(CollectionUtils.isEmpty(superheroNew.getSuperpowers()));
	}

}
