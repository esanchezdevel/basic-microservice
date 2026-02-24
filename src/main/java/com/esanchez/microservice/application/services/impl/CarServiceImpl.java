package com.esanchez.microservice.application.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.CarService;
import com.esanchez.microservice.domain.model.CarEntity;

@Service
public class CarServiceImpl implements CarService {

	private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);
	
	@Override
	public CarEntity saveEntity(CarEntity entity) throws ApiException {
		logger.info("Saving entity in database. {}", entity);
		
		return null;
	}
}
