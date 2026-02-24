package com.esanchez.microservice.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esanchez.microservice.domain.model.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Long>{

}
