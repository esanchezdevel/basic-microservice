package com.esanchez.microservice.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.esanchez.microservice.application.dto.CarDTO;
import com.esanchez.microservice.application.dto.CarReadAllResponseDTO;
import com.esanchez.microservice.application.dto.ResponseDTO;
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
		
		ResponseEntity<ResponseDTO> response = carController.create(carDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(carDTO.toString(), response.getBody().getBody().toString());
		
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
		
		ResponseDTO expectedResponse = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Unexpected error");
		
		when(carMappingMock.parseToEntity(any(CarDTO.class))).thenReturn(carEntity);
		when(carServiceMock.saveEntity(any(CarEntity.class))).thenThrow(new ApiException(HttpStatus.BAD_REQUEST.value(), "Unexpected error"));
		
		ResponseEntity<ResponseDTO> response = carController.create(carDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(expectedResponse.toString(), response.getBody().toString());
		
		verify(carMappingMock, times(1)).parseToEntity(any(CarDTO.class));
		verify(carServiceMock, times(1)).saveEntity(any(CarEntity.class));
		verify(carMappingMock, times(0)).parseToDto(any(CarEntity.class));
	}
	
	/**
	 * Tests GET read all cars
	 */
	@Test
	@DisplayName("When there are cars in db then return list")
	void whenThereAreCarsInDatabaseThenReturnList() {
		
		BrandEntity brand1 = new BrandEntity();
		brand1.setId(1L);
		brand1.setName("test-brand1");
		
		CarEntity car1 = new CarEntity();
		car1.setId(1L);
		car1.setBrand(brand1);
		car1.setModel("test-model1");
		car1.setOwner("test-owner1");
		car1.setLicense("1111-T");

		BrandEntity brand2 = new BrandEntity();
		brand2.setId(2L);
		brand2.setName("test-brand2");
		
		CarEntity car2 = new CarEntity();
		car2.setId(2L);
		car2.setBrand(brand2);
		car2.setModel("test-model2");
		car2.setOwner("test-owner2");
		car2.setLicense("1112-T");
		
		CarDTO carDTO1 = new CarDTO();
		carDTO1.setId("1");
		carDTO1.setBrand("test-brand1");
		carDTO1.setModel("test-model1");
		carDTO1.setOwner("test-owner1");
		carDTO1.setLicense("1111-T");
		
		CarDTO carDTO2 = new CarDTO();
		carDTO1.setId("2");
		carDTO1.setBrand("test-brand2");
		carDTO1.setModel("test-model2");
		carDTO1.setOwner("test-owner2");
		carDTO1.setLicense("1112-T");
		
		List<CarEntity> cars = List.of(car1, car2);
		Page<CarEntity> page = new PageImpl<CarEntity>(cars);
		List<CarDTO> carsDTOs = List.of(carDTO1, carDTO2);
		
		when(carServiceMock.getAllEntities(any(Pageable.class))).thenReturn(page);
		when(carMappingMock.parseToDtoList(anyList())).thenReturn(carsDTOs);
		
		ResponseEntity<ResponseDTO> result = carController.readAll(0, 10);
		
		assertNotNull(result.getBody());
		assertEquals(2, ((CarReadAllResponseDTO) result.getBody().getBody()).getCars().size());
	}
}
