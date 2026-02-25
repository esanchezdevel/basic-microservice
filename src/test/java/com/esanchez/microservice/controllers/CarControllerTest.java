package com.esanchez.microservice.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.esanchez.microservice.application.dto.CarDTO;
import com.esanchez.microservice.application.dto.BaseDTO;
import com.esanchez.microservice.application.dto.mapping.Mapping;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.CarService;
import com.esanchez.microservice.domain.model.BrandEntity;
import com.esanchez.microservice.domain.model.CarEntity;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

	@Mock private CarService carServiceMock;
	@Mock private Mapping<CarDTO, CarEntity> carMappingMock;
	
	@InjectMocks
	private CarController carController;
	
	/**
	 * Tests POST create car
	 */
	@Test
	@DisplayName("Given a valid entity then success")
	void givenValidEntityWhenSaveThenReturnSuccess() {
		
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(1L);
		brandEntity.setName("test-brand");
		
		CarEntity carEntity = new CarEntity();
		carEntity.setId(1L);
		carEntity.setBrand(brandEntity);
		carEntity.setModel("test-model");
		carEntity.setOwner("test-owner");
		carEntity.setLicense("1111-T");
		
		CarDTO carDTO = new CarDTO();
		carDTO.setBrand("test-brand");
		carDTO.setModel("test-model");
		carDTO.setOwner("test-owner");
		carDTO.setLicense("1111-T");
		
		when(carMappingMock.parseToEntity(any(CarDTO.class))).thenReturn(carEntity);
		when(carServiceMock.saveEntity(any(CarEntity.class))).thenReturn(carEntity);
		when(carMappingMock.parseToDto(any(CarEntity.class))).thenReturn(carDTO);
		
		ResponseEntity<BaseDTO> response = carController.create(carDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(carDTO.toString(), response.getBody().toString());
		
		verify(carMappingMock, times(1)).parseToEntity(any(CarDTO.class));
		verify(carServiceMock, times(1)).saveEntity(any(CarEntity.class));
		verify(carMappingMock, times(1)).parseToDto(any(CarEntity.class));
	}
	
	@Test
	@DisplayName("Given an invalid entity then error")
	void givenInvalidEntityWhenSaveThenReturnError() {
		
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(1L);
		brandEntity.setName("test-brand");
		
		CarEntity carEntity = new CarEntity();
		carEntity.setId(1L);
		carEntity.setBrand(brandEntity);
		carEntity.setModel("test-model");
		carEntity.setOwner("test-owner");
		carEntity.setLicense("1111-T");
		
		CarDTO carDTO = new CarDTO();
		carDTO.setBrand("test-brand");
		carDTO.setModel("test-model");
		carDTO.setOwner("test-owner");
		carDTO.setLicense("1111-T");
		
		BaseDTO expectedResponse = new BaseDTO(HttpStatus.BAD_REQUEST.value(), "Unexpected error");
		
		when(carMappingMock.parseToEntity(any(CarDTO.class))).thenReturn(carEntity);
		when(carServiceMock.saveEntity(any(CarEntity.class))).thenThrow(new ApiException(HttpStatus.BAD_REQUEST.value(), "Unexpected error"));
		
		ResponseEntity<BaseDTO> response = carController.create(carDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(expectedResponse.toString(), response.getBody().toString());
		
		verify(carMappingMock, times(1)).parseToEntity(any(CarDTO.class));
		verify(carServiceMock, times(1)).saveEntity(any(CarEntity.class));
		verify(carMappingMock, times(0)).parseToDto(any(CarEntity.class));
	}
}
