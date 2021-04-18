package com.hiberus.superhero.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.superhero.dto.SuperheroDTO;
import com.hiberus.superhero.model.Superhero;
import com.hiberus.superhero.model.Superpower;
import com.hiberus.superhero.payload.SuperheroSuperpowerRequest;
import com.hiberus.superhero.repository.SuperheroRepository;
import com.hiberus.superhero.repository.SuperpowerRepository;
import com.hiberus.superhero.service.SuperheroService;

@Service
@Transactional
@CacheConfig(cacheNames = "Superhero")
public class SuperheroServiceImpl implements SuperheroService {

	@Autowired
	private SuperheroRepository superheroRepository;

	@Autowired
	private SuperpowerRepository superpowerRepository;

	@Resource(name = "objectMapperForSuperhero")
	private ObjectMapper objectMapperForSuperhero;

	@Override
	@Cacheable(key = "{ #root.methodName, #id }")
	public Optional<SuperheroDTO> findById(Long id) {
		Optional<Superhero> superhero = superheroRepository.findById(id);
		if (Boolean.FALSE.equals(superhero.isPresent())) {
			return Optional.empty();
		}

		return Optional.ofNullable(objectMapperForSuperhero.convertValue(superhero.get(), SuperheroDTO.class));
	}

	@Override
	@Cacheable(key = "{ #root.methodName }")
	public Collection<SuperheroDTO> findAll() {
		return superheroRepository.findAll().stream()
				.map(superhero -> objectMapperForSuperhero.convertValue(superhero, SuperheroDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Cacheable(key = "{ #root.methodName, #name }")
	public Collection<SuperheroDTO> findAllByName(String name) {
		return superheroRepository.findAllByName(name).stream()
				.map(superhero -> objectMapperForSuperhero.convertValue(superhero, SuperheroDTO.class))
				.collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	@CacheEvict(key = "{ #root.methodName }" , allEntries = true)
	public SuperheroDTO save(SuperheroDTO superheroDTO) {
		Superhero supeheroNew = objectMapperForSuperhero.convertValue(superheroDTO, Superhero.class);
		Superhero superhero = superheroRepository.save(supeheroNew);
		return objectMapperForSuperhero.convertValue(superhero, SuperheroDTO.class);
	}

	@Override
	@CacheEvict(key = "{ #root.methodName }" , allEntries = true)
	public void deleteById(Long id) {
		superheroRepository.deleteById(id);
	}

	@Override
	@CacheEvict(key = "{ #root.methodName, #superheroId, #superpowerId }", allEntries = true)
	public Boolean addSuperpowerToSuperhero(SuperheroSuperpowerRequest superheroSuperpowerRequest) {
		Boolean result = Boolean.FALSE;

		Optional<Superhero> superhero = superheroRepository.findById(superheroSuperpowerRequest.getSuperheroId());
		if (superhero.isPresent()) {
			Optional<Superpower> superpower = superpowerRepository
					.findById(superheroSuperpowerRequest.getSuperpowerId());
			if (superpower.isPresent()) {
				result = superhero.get().addSuperpower(superpower.get());
				if (Boolean.FALSE.equals(result)) {
					throw new IllegalArgumentException(getErrorMsg(superheroSuperpowerRequest.getSuperpowerId(),
							superheroSuperpowerRequest.getSuperheroId(), "is already assigned to"));
				}
				superheroRepository.save(superhero.get());
			}
		}
		return result;
	}

	@Override
	@CacheEvict(key = "{ #root.methodName, #superheroId, #superpowerId }", allEntries = true)
	public Boolean deleteSuperpowerFromSuperhero(Long superheroId, Long superpowerId) {
		Boolean result = Boolean.FALSE;

		Optional<Superhero> superhero = superheroRepository.findById(superheroId);
		if (superhero.isPresent() && CollectionUtils.isNotEmpty(superhero.get().getSuperpowers())) {
			Optional<Superpower> superpower = superpowerRepository.findById(superpowerId);
			if (superpower.isPresent()) {
				result = superhero.get().removeSuperpower(superpower.get());
				if (Boolean.FALSE.equals(result)) {
					throw new IllegalArgumentException(getErrorMsg(superpowerId, superheroId, "is not related to"));
				}

				superheroRepository.save(superhero.get());
			}
		}
		return result;
	}

	private static final String getErrorMsg(Long value1, Long value2, String errorMsg) {
		return new StringBuilder().append(Superpower.class.getSimpleName()).append(" with ID:").append(value1)
				.append(StringUtils.SPACE).append(errorMsg).append(StringUtils.SPACE)
				.append(Superhero.class.getSimpleName()).append(" with ID:").append(value2).toString();
	}
}
