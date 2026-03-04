package com.esanchez.microservice.application.services.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.CarService;
import com.esanchez.microservice.domain.model.CarEntity;
import com.esanchez.microservice.domain.repositories.CarRepository;

@Service
public class CarServiceImpl implements CarService {

	private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);
	
	private final CarRepository carRepository;
	
	public CarServiceImpl(CarRepository carRepository) {
		this.carRepository = carRepository;
	}
	
	@Override
	public CarEntity saveEntity(CarEntity entity) throws ApiException {
		logger.info("Saving entity in database. {}", entity);
		
		validateEntity(entity);

		try {
			return carRepository.save(entity);
		} catch (Exception e) {
			logger.error("Unexpected error saving entity: {}. {}", entity, e.getMessage());
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error saving entity: " + entity + ". " + e.getMessage());
		}
	}

	private void validateEntity(CarEntity entity) throws ApiException {
		if (entity == null) 
			throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid car entity");
		if (entity.getBrand() == null || entity.getBrand().getName() == null || entity.getBrand().getName().length() == 0) 
			throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid car entity. Brand is empty");
		if (entity.getModel() == null || entity.getModel().length() == 0) 
			throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid car entity. Model is empty");
		if (entity.getOwner() == null || entity.getOwner().length() == 0) 
			throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid car entity. Owner is empty");
		if (entity.getLicense() == null || entity.getLicense().length() == 0) 
			throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid car entity. License is empty");
	}
	
	@Override
	public Page<CarEntity> getAllEntities(Pageable pageable) throws ApiException {
		logger.info("Getting all Cars from database");
		try {
			return carRepository.findAll(pageable);
		} catch (Exception e) {
			logger.error("Unexpected error getting all entities from database. {}", e.getMessage());
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error getting all entities from database. " + e.getMessage());
		}
	}
	
	@Override
	public Optional<CarEntity> getEntity(Long id) throws ApiException {
		logger.info("Getting car entity with id {}", id);
		
		try {
			return carRepository.findById(id);
		} catch (Exception e) {
			logger.error("Unexpected error getting entity with id {}. {}", id, e.getMessage());
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public CarEntity update(Long id, CarEntity carEntity) throws ApiException {
		logger.info("Updating car entity with id {}. Entity: {}", id, carEntity);
		
		try {
			Optional<CarEntity> dbCarEntity = carRepository.findById(id);
			
			if (dbCarEntity.isEmpty())
				throw new ApiException(HttpStatus.NOT_FOUND.value(), "Car not found in database");
		
			dbCarEntity.get().setId(carEntity.getId());
			dbCarEntity.get().setBrand(carEntity.getBrand());
			dbCarEntity.get().setModel(carEntity.getModel());
			dbCarEntity.get().setOwner(carEntity.getOwner());
			dbCarEntity.get().setLicense(carEntity.getLicense());
			
			return dbCarEntity.get();
		} catch (ApiException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error getting entity from database. {}", e.getMessage());
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}
}
