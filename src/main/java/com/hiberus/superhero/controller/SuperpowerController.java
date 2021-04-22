package com.hiberus.superhero.controller;

import java.net.URI;
import java.time.Instant;
import java.util.Collection;

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

import com.hiberus.superhero.controller.constant.ConstantControllers;
import com.hiberus.superhero.dto.SuperpowerDTO;
import com.hiberus.superhero.exceptions.ResourceNotFoundException;
import com.hiberus.superhero.service.SuperpowerService;

@RestController
@RequestMapping("/api/superpowers")
public class SuperpowerController {
	private final Logger LOGGER = LoggerFactory.getLogger(SuperpowerController.class);

	private final SuperpowerService superpowerService;

	public SuperpowerController(SuperpowerService superpowerService) {
		this.superpowerService = superpowerService;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuperpowerDTO> getById(@PathVariable Long id) {
		LOGGER.debug("REST request to getById Superpower: {}", id);
		SuperpowerDTO superpowerDTO = superpowerService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ConstantControllers.SUPERPOWER_ENTITY, ConstantControllers.ID, id.toString()));

		return ResponseEntity.ok().body(superpowerDTO);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<SuperpowerDTO>> getAll() {
		LOGGER.debug("REST request to getAll Superpower");
		Collection<SuperpowerDTO> superpowers = superpowerService.findAll();
		if (CollectionUtils.isEmpty(superpowers)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(superpowers);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody SuperpowerDTO superpowerDTO) {
		LOGGER.debug("REST request to save Superpower: {}", superpowerDTO);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		superpowerDTO.setCreatedBy(userDetails.getUsername());
		superpowerDTO.setCreatedDate(Instant.now());
		SuperpowerDTO superpowerSaved = superpowerService.save(superpowerDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(superpowerSaved.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuperpowerDTO> update(@RequestBody SuperpowerDTO superpowerDTO) {
		LOGGER.debug("REST request to update Superpower: {}", superpowerDTO);

		SuperpowerDTO superpowerSaved = superpowerService.findById(superpowerDTO.getId()).orElseThrow(
				() -> new ResourceNotFoundException(ConstantControllers.SUPERPOWER_ENTITY, ConstantControllers.ID, superpowerDTO.getId().toString()));

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SuperpowerDTO superpowerToEdit = new SuperpowerDTO(superpowerDTO.getName(), superpowerDTO.getDescription());
		superpowerToEdit.setId(superpowerSaved.getId());
		superpowerToEdit.setLastModifiedBy(userDetails.getUsername());
		superpowerToEdit.setLastModifiedDate(Instant.now());
		return ResponseEntity.ok().body(superpowerService.save(superpowerToEdit));
	}

	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteByID(@PathVariable Long id) {
		SuperpowerDTO superpowerDTO = superpowerService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ConstantControllers.SUPERPOWER_ENTITY, ConstantControllers.ID, id.toString()));
		superpowerService.deleteById(superpowerDTO.getId());
		return ResponseEntity.noContent().build();
	}
}
