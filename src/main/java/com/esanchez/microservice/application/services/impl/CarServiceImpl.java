package com.esanchez.microservice.application.services.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esanchez.microservice.application.event.CarCreationEvent;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.port.CarProducer;
import com.esanchez.microservice.application.services.CarService;
import com.esanchez.microservice.domain.model.CarEntity;
import com.esanchez.microservice.domain.repositories.CarRepository;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

@Service
public class CarServiceImpl implements CarService {

	private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);
	
	private final CarRepository carRepository;
	
	private final CarProducer<String, String> carProducer;
	
	public CarServiceImpl(CarRepository carRepository, CarProducer<String, String> carProducer) {
		this.carRepository = carRepository;
		this.carProducer = carProducer;
	}
	
	@Override
	public CarEntity saveEntity(CarEntity entity) throws ApiException {
		logger.info("Saving entity in database. {}", entity);
		
		validateEntity(entity);

		try {
			CarEntity savedEntity = carRepository.save(entity);
			
			CarCreationEvent carCreationEvent = new CarCreationEvent.Builder()
											.id(savedEntity.getId())
											.brand(savedEntity.getBrand().getName())
											.model(savedEntity.getModel())
											.build();
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			carProducer.produce("cars-created", ow.writeValueAsString(carCreationEvent));
			
			return savedEntity;
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
	public CarEntity updateEntity(Long id, CarEntity carEntity) throws ApiException {
		logger.info("Updating car entity with id {}. Entity: {}", id, carEntity);
		
		try {
			Optional<CarEntity> dbCarEntity = carRepository.findById(id);
			
			if (dbCarEntity.isEmpty())
				throw new ApiException(HttpStatus.NOT_FOUND.value(), "Car not found in database");
		
			CarEntity entity = dbCarEntity.get();
			
			entity.setBrand(carEntity.getBrand());
			entity.setModel(carEntity.getModel());
			entity.setOwner(carEntity.getOwner());
			entity.setLicense(carEntity.getLicense());
			
			return entity;
		} catch (Exception e) {
			logger.error("Unexpected error getting entity from database. {}", e.getMessage());
			
			if (e instanceof ApiException) throw e;
			
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public CarEntity partialUpdateEntity(Long id, CarEntity carEntity) throws ApiException {
		logger.info("Partial Updating car entity with id {}. Entity: {}", id, carEntity);
		
		try {
			Optional<CarEntity> dbCarEntity = carRepository.findById(id);
			
			if (dbCarEntity.isEmpty())
				throw new ApiException(HttpStatus.NOT_FOUND.value(), "Car not found in database");
		
			CarEntity entity = dbCarEntity.get();
			
			if (carEntity.getBrand() != null) entity.setBrand(carEntity.getBrand());
			if (carEntity.getModel() != null) entity.setModel(carEntity.getModel());
			if (carEntity.getOwner() != null) entity.setOwner(carEntity.getOwner());
			if (carEntity.getLicense() != null) entity.setLicense(carEntity.getLicense());
			
			return entity;
		} catch (Exception e) {
			logger.error("Unexpected error getting entity from database. {}", e.getMessage());
			
			if (e instanceof ApiException) throw e;
			
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}
	
	@Override
	public void deleteEntity(Long id) throws ApiException {
		logger.info("Deleting car entity with id {}", id);
		
		try {
			carRepository.deleteById(id);
		} catch (Exception e) {
			logger.error("Unexpected error deleting entity from database. {}", e.getMessage());
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}
}
