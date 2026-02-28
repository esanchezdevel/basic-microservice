package com.esanchez.microservice.application.dto;

import java.util.List;

public class CarReadAllResponseDTO extends BaseDTO {

	private List<CarDTO> cars;

	public List<CarDTO> getCars() {
		return cars;
	}

	public void setCars(List<CarDTO> cars) {
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "CarReadAllResponseDTO [cars=" + cars + "]";
	}
}
