package com.esanchez.microservice.application.dto.mapping;

import com.esanchez.microservice.application.dto.CarDTO;
import com.esanchez.microservice.domain.model.CarEntity;

public class CarMapping {

	public static CarDTO parseToDto(CarEntity entity) {
		CarDTO dto = new CarDTO();
		dto.setBrand(entity.getBrand().getName());
		dto.setModel(entity.getModel());
		dto.setOwner(entity.getOwner());
		dto.setLicense(entity.getLicense());
		return dto;
	}
}
