package com.hiberus.superhero.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hiberus.superhero.model.Superhero;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

	@Query("SELECT s FROM Superhero s WHERE UPPER(s.name) LIKE CONCAT('%', UPPER(:name),'%')")
	Collection<Superhero> findAllByName(@Param("name") String name);
}
