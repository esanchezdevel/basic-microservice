package com.esanchez.microservice.application.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.domain.model.CarEntity;

public interface CarService {

	/**
	 * Save one Car entity in database.
	 * 
	 * @param entity The Car to be saved in database
	 * @return The entity saved in database
	 * @throws ApiException If any error happens during the process
	 */
	CarEntity saveEntity(CarEntity entity) throws ApiException;
	
	/**
	 * Get all the Car entities from database
	 * 
	 * @return List of Car entities
	 * @throws ApiException
	 */
	Page<CarEntity> getAllEntities(Pageable pageable) throws ApiException;
	
	/**
	 * Get one Car entity from database looking for the id
	 * 
	 * @param id The id of the entity
	 * @return Optional of CarEntity found in database.
	 * @throws ApiException
	 */
	Optional<CarEntity> getEntity(Long id) throws ApiException;
	
	/**
	 * Update the full entity in database.
	 * If the entity is not present in database, an ApiException is thrown.
	 * 
	 * @param id CarEntity id
	 * @param carEntity The full entity to be persisted in database
	 * @return The carEntity updated
	 * @throws ApiException
	 */
	CarEntity update(Long id, CarEntity carEntity) throws ApiException;
}
