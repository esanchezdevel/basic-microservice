package com.esanchez.microservice.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esanchez.microservice.domain.model.AppUserEntity;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long>{

	Optional<AppUserEntity> findByUsername(String username);
}
