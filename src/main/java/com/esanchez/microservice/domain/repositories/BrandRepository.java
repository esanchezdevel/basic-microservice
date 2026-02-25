package com.esanchez.microservice.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esanchez.microservice.domain.model.BrandEntity;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

	Optional<BrandEntity> findByName(String name);
}
