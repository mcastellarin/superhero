package com.hiberus.superhero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiberus.superhero.model.Superpower;

public interface SuperpowerRepository extends JpaRepository<Superpower, Long> {

}
