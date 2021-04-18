package com.hiberus.superhero.service;

import java.util.Collection;
import java.util.Optional;

import com.hiberus.superhero.dto.SuperheroDTO;
import com.hiberus.superhero.payload.SuperheroSuperpowerRequest;

public interface SuperheroService {

	Optional<SuperheroDTO> findById(Long id);

	Collection<SuperheroDTO> findAll();

	Collection<SuperheroDTO> findAllByName(String name);

	SuperheroDTO save(SuperheroDTO superhero);

	void deleteById(Long id);

	Boolean addSuperpowerToSuperhero(SuperheroSuperpowerRequest superheroSuperpower);

	Boolean deleteSuperpowerFromSuperhero(Long superheroId, Long superpowerId);
}
