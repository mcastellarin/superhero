package com.hiberus.superhero.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.superhero.dto.SuperpowerDTO;
import com.hiberus.superhero.model.Superpower;
import com.hiberus.superhero.repository.SuperpowerRepository;
import com.hiberus.superhero.service.SuperpowerService;

@Service
public class SuperpowerServiceImpl implements SuperpowerService {

	private final SuperpowerRepository superpowerRepository;

	@Resource(name = "objectMapperForSuperhero")
	private ObjectMapper objectMapperForSuperhero;

	public SuperpowerServiceImpl(SuperpowerRepository superpowerRepository) {
		this.superpowerRepository = superpowerRepository;
	}

	@Override
	public Optional<SuperpowerDTO> findById(Long id) {
		Optional<com.hiberus.superhero.model.Superpower> superpower = superpowerRepository.findById(id);
		if (Boolean.FALSE.equals(superpower.isPresent())) {
			return Optional.empty();
		}

		return Optional.ofNullable(objectMapperForSuperhero.convertValue(superpower.get(), SuperpowerDTO.class));
	}

	@Override
	public Collection<SuperpowerDTO> findAll() {
		return superpowerRepository.findAll().stream()
				.map(superhero -> objectMapperForSuperhero.convertValue(superhero, SuperpowerDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public SuperpowerDTO save(SuperpowerDTO superpowerDTO) {
		Superpower superpower = superpowerRepository
				.save(objectMapperForSuperhero.convertValue(superpowerDTO, Superpower.class));
		return objectMapperForSuperhero.convertValue(superpower, SuperpowerDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		superpowerRepository.deleteById(id);
	}
}
