package com.esanchez.microservice.application.dto.mapping;

import com.esanchez.microservice.application.dto.CarDTO;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.domain.model.CarEntity;

public interface CarMapping {

	CarDTO parseToDto(CarEntity entity);
	
	CarEntity parseToEntity(CarDTO dto) throws ApiException;
}
