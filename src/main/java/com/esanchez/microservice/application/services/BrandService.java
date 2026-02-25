package com.esanchez.microservice.application.services;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.domain.model.BrandEntity;

public interface BrandService {

	/**
	 * Save one Brand entity in database.
	 * 
	 * @param entity The Brand to be saved in database
	 * @return The entity saved in database
	 * @throws ApiException If any error happens during the process
	 */
	BrandEntity saveEntity(BrandEntity entity) throws ApiException;
}
