package com.hiberus.superhero.controller;

import java.net.URI;
import java.time.Instant;
import java.util.Collection;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hiberus.superhero.aspects.annotation.MethodExecutionInformationAnnotation;
import com.hiberus.superhero.controller.constant.ConstantControllers;
import com.hiberus.superhero.dto.SuperheroDTO;
import com.hiberus.superhero.exceptions.ResourceNotFoundException;
import com.hiberus.superhero.payload.SuperheroSuperpowerRequest;
import com.hiberus.superhero.service.SuperheroService;

@RestController
@RequestMapping("/api/superheroes")
public class SuperheroController {
	private final Logger LOGGER = LoggerFactory.getLogger(SuperheroController.class);

	private final SuperheroService superheroService;

	public SuperheroController(SuperheroService superheroService) {
		this.superheroService = superheroService;
	}

	@MethodExecutionInformationAnnotation
	@GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuperheroDTO> getById(@PathVariable Long id) {
		LOGGER.debug("REST request to getById " + ConstantControllers.SUPERHERO_ENTITY + " for id: {}", id);
		SuperheroDTO superheroDTO = superheroService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ConstantControllers.SUPERHERO_ENTITY, ConstantControllers.ID, id.toString()));

		return ResponseEntity.ok().body(superheroDTO);
	}

	@MethodExecutionInformationAnnotation
	@GetMapping(value = "/getAllByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<SuperheroDTO>> getAllByName(@PathVariable String name) {
		LOGGER.debug("REST request to getAllByName " + ConstantControllers.SUPERHERO_ENTITY + " for name: {}", name);
		Collection<SuperheroDTO> superheroes = superheroService.findAllByName(name);
		if (CollectionUtils.isEmpty(superheroes)) {
			throw new ResourceNotFoundException(ConstantControllers.SUPERHERO_ENTITY, "name", name);
		}

		return ResponseEntity.ok().body(superheroes);
	}

	@MethodExecutionInformationAnnotation
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<SuperheroDTO>> getAll() {
		LOGGER.debug("REST request to getAll " + ConstantControllers.SUPERHERO_ENTITY);
		Collection<SuperheroDTO> superheroes = superheroService.findAll();
		if (CollectionUtils.isEmpty(superheroes)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(superheroes);
	}

	@MethodExecutionInformationAnnotation
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody SuperheroDTO superheroDTO) {
		LOGGER.debug("REST request to save " + ConstantControllers.SUPERHERO_ENTITY + " with data: {}", superheroDTO);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		superheroDTO.setCreatedBy(userDetails.getUsername());
		superheroDTO.setCreatedDate(Instant.now());
		SuperheroDTO superheroSaved = superheroService.save(superheroDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(superheroSaved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@MethodExecutionInformationAnnotation
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuperheroDTO> update(@Valid @RequestBody SuperheroDTO superheroDTO) {
		LOGGER.debug("REST request to update " + ConstantControllers.SUPERHERO_ENTITY + " with data: {}", superheroDTO);
		SuperheroDTO superheroSaved = superheroService.findById(superheroDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException(ConstantControllers.SUPERHERO_ENTITY, ConstantControllers.ID, superheroDTO.getId().toString()));

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		SuperheroDTO superheroToEdit = new SuperheroDTO(superheroDTO.getName(), superheroDTO.getSecretIdentity(), superheroDTO.getNameSecretIdentity(), superheroDTO.getOrigin(), null);
		superheroToEdit.setId(superheroSaved.getId());
		superheroToEdit.setLastModifiedBy(userDetails.getUsername());
		superheroToEdit.setLastModifiedDate(Instant.now());
		return ResponseEntity.ok().body(superheroService.save(superheroToEdit));
	}

	@MethodExecutionInformationAnnotation
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteByID(@PathVariable Long id) {
		LOGGER.debug("REST request to delete " + ConstantControllers.SUPERHERO_ENTITY + " for id: {}", id);
		SuperheroDTO superheroDTO = superheroService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ConstantControllers.SUPERHERO_ENTITY, ConstantControllers.ID, id.toString()));
		superheroService.deleteById(superheroDTO.getId());
		return ResponseEntity.noContent().build();
	}

	@MethodExecutionInformationAnnotation
	@PostMapping(value = "/addSuperpowerToSuperhero", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addSuperpowerToSuperhero(@Valid @RequestBody SuperheroSuperpowerRequest superheroSuperpowerRequest) {
		evaluateResultOperation(superheroService.addSuperpowerToSuperhero(superheroSuperpowerRequest),
				superheroSuperpowerRequest.getSuperheroId().toString(),
				superheroSuperpowerRequest.getSuperpowerId().toString());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{superheroId}{superpowerId}")
				.buildAndExpand(superheroSuperpowerRequest.getSuperheroId(), superheroSuperpowerRequest.getSuperpowerId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@MethodExecutionInformationAnnotation
	@DeleteMapping(value = "/deleteSuperpowerFromSuperhero/{superheroId}/{superpowerId}")
	public ResponseEntity<?> deleteSuperpowerFromSuperhero(@PathVariable Long superheroId, @PathVariable Long superpowerId) {
		evaluateResultOperation(superheroService.deleteSuperpowerFromSuperhero(superheroId, superpowerId),
				superheroId.toString(), superpowerId.toString());
		return ResponseEntity.noContent().build();
	}

	private static void evaluateResultOperation(Boolean result, String valueParam1, String valueParam2) {
		if(Boolean.FALSE.equals(result)) {
			throw new ResourceNotFoundException(ConstantControllers.SUPERHERO_ENTITY, ConstantControllers.SUPERPOWER_ENTITY, ConstantControllers.ID, valueParam1, valueParam2);
		}
	}
}
