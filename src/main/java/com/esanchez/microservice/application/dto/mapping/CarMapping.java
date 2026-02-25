package com.esanchez.microservice.application.dto.mapping;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esanchez.microservice.application.dto.CarDTO;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.domain.model.BrandEntity;
import com.esanchez.microservice.domain.model.CarEntity;
import com.esanchez.microservice.domain.repositories.BrandRepository;

@Service
public class CarMapping implements Mapping<CarDTO, CarEntity> {

	private final BrandRepository brandRepository;
	
	public CarMapping(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}
	
	public CarDTO parseToDto(CarEntity entity) {
		CarDTO dto = new CarDTO();
		dto.setBrand(entity.getBrand().getName());
		dto.setModel(entity.getModel());
		dto.setOwner(entity.getOwner());
		dto.setLicense(entity.getLicense());
		return dto;
	}
	
	public CarEntity parseToEntity(CarDTO dto) throws ApiException {
		
		Optional<BrandEntity> brand = brandRepository.findByName(dto.getBrand());
		
		if (brand.isEmpty())
			throw new ApiException(HttpStatus.NOT_FOUND.value(), "Brand '{}' not found in database");
		
		CarEntity entity = new CarEntity();
		entity.setBrand(brand.get());
		entity.setModel(dto.getModel());
		entity.setOwner(dto.getOwner());
		entity.setLicense(dto.getLicense());
		
		return entity;
	}
}
