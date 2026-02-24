package com.esanchez.microservice.application.services;

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
}
