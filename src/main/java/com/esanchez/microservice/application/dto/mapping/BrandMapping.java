package com.esanchez.microservice.application.dto.mapping;

import org.springframework.stereotype.Service;

import com.esanchez.microservice.application.dto.BrandDTO;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.domain.model.BrandEntity;

@Service
public class BrandMapping implements Mapping<BrandDTO, BrandEntity> {

	@Override
	public BrandDTO parseToDto(BrandEntity entity) {
		BrandDTO dto = new BrandDTO();
		dto.setName(entity.getName());
		return dto;
	}
	
	@Override
	public BrandEntity parseToEntity(BrandDTO dto) throws ApiException {
		BrandEntity entity = new BrandEntity();
		entity.setName(dto.getName());
		return entity;
	}
}
