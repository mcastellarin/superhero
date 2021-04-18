package com.hiberus.superhero.service;

import java.util.Collection;
import java.util.Optional;

import com.hiberus.superhero.dto.SuperpowerDTO;

public interface SuperpowerService {
	Optional<SuperpowerDTO> findById(Long id);

	Collection<SuperpowerDTO> findAll();

	SuperpowerDTO save(SuperpowerDTO superpower);

	void deleteById(Long id);
}
