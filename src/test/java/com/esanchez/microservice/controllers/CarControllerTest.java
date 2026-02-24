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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.esanchez.microservice.application.dto.CarDTO;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.CarService;
import com.esanchez.microservice.domain.model.BrandEntity;
import com.esanchez.microservice.domain.model.CarEntity;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

	@Mock
	private CarService carServiceMock;
	
	private CarController carController = new CarController(carServiceMock);
	
	/**
	 * Tests POST create car
	 */
	@Test
	@DisplayName("Given a valid entity then success")
	void givenValidEntityWhenSaveThenReturnSuccess() {
		
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(1L);
		brandEntity.setName("test-brand");
		
		CarEntity savedCarEntity = new CarEntity();
		savedCarEntity.setId(1L);
		savedCarEntity.setBrand(brandEntity);
		savedCarEntity.setModel("test-model");
		savedCarEntity.setOwner("test-owner");
		savedCarEntity.setLicense("1111-T");
		
		CarDTO carDTO = new CarDTO();
		carDTO.setBrand("test-brand");
		carDTO.setModel("test-model");
		carDTO.setOwner("test-owner");
		carDTO.setLicense("1111-T");
		
		when(carServiceMock.saveEntity(any(CarEntity.class))).thenReturn(savedCarEntity);
		
		ResponseEntity<CarDTO> response = carController.create(carDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(savedCarEntity.toString(), response.getBody());
		
		verify(carServiceMock, times(1)).saveEntity(any(CarEntity.class));
	}
	
	@Test
	@DisplayName("Given an invalid entity then error")
	void givenInvalidEntityWhenSaveThenReturnError() {
		
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(1L);
		brandEntity.setName("test-brand");
		
		CarEntity savedCarEntity = new CarEntity();
		savedCarEntity.setId(1L);
		savedCarEntity.setBrand(brandEntity);
		savedCarEntity.setModel("test-model");
		savedCarEntity.setOwner("test-owner");
		savedCarEntity.setLicense("1111-T");
		
		CarDTO carDTO = new CarDTO("test-brand", "test-model", "test-owner", "1111-T");
		
		when(carServiceMock.saveEntity(any(CarEntity.class))).thenThrow(new ApiException(HttpStatus.BAD_REQUEST.value(), "Unexpected error"));
		
		ResponseEntity<CarDTO> response = carController.create(carDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		// TODO assert the body with the error message
		
		verify(carServiceMock, times(1)).saveEntity(any(CarEntity.class));
	}
}
