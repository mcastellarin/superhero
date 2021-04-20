package com.hiberus.superhero.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hiberus.superhero.dto.SuperheroDTO;
import com.hiberus.superhero.model.Superhero;
import com.hiberus.superhero.model.enums.Origin;
import com.hiberus.superhero.model.enums.SecretIdentity;
import com.hiberus.superhero.repository.SuperheroRepository;
import com.hiberus.superhero.repository.SuperpowerRepository;
import com.hiberus.superhero.service.SuperheroService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SuperheroServiceImplTest.config.class })
public class SuperheroServiceImplTest {

	@TestConfiguration
	static class config {
		@Bean
		public SuperheroService superheroService() {
			return new SuperheroServiceImpl();
		}
	}

	@Autowired
	private SuperheroService superheroService;

	@MockBean
	private SuperheroRepository superheroRepository;

	@MockBean
	private SuperpowerRepository superpowerRepository;

	private Superhero superhero;
	private static final String SEARCH_BY_NAME = "man";

	@Before
	public void setUp() {
		List<Superhero> superheroes = new ArrayList<>();
		superhero = new Superhero("BAT-MAN", SecretIdentity.CIVIL_IDENTITY, "BRUCE WAYNE", Origin.OTHERS, new HashSet<>());
		superheroes.add(superhero);
		Mockito.when(superheroRepository.findAllByName(SEARCH_BY_NAME)).thenReturn(superheroes);
	}

	@Test
	public void findByAllName() {
		Collection<SuperheroDTO> superheroes = superheroService.findAllByName(SEARCH_BY_NAME);
		assertFalse(CollectionUtils.isEmpty(superheroes));
		assertThat(superheroes.size()).isEqualTo(NumberUtils.INTEGER_ONE);
		assertTrue(superheroes.stream().findFirst().isPresent());
		assertThat(superheroes.stream().findFirst().get().getName()).isEqualTo(superhero.getName());
	}
}
