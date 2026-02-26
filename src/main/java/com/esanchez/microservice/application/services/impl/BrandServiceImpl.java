package com.esanchez.microservice.application.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.BrandService;
import com.esanchez.microservice.domain.model.BrandEntity;
import com.esanchez.microservice.domain.repositories.BrandRepository;

@Service
public class BrandServiceImpl implements BrandService {

	private static final Logger logger = LogManager.getLogger(BrandServiceImpl.class);
	
	private final BrandRepository brandRepository;
	
	public BrandServiceImpl(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}
	
	@Override
	public BrandEntity saveEntity(BrandEntity entity) throws ApiException {
		logger.info("Saving brand entity in database: {}", entity);
		
		if (entity.getName() == null || entity.getName().length() == 0)
			throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid brand entity");

		try {
			return brandRepository.save(entity);
		} catch (Exception e) {
			logger.error("Unexpected error saving entity: {}. {}", entity, e.getMessage());
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error saving entity: " + entity + ". " + e.getMessage());
		}
		
	}
}
