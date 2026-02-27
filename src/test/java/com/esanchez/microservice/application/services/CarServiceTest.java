package com.esanchez.microservice.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.impl.CarServiceImpl;
import com.esanchez.microservice.domain.model.BrandEntity;
import com.esanchez.microservice.domain.model.CarEntity;
import com.esanchez.microservice.domain.repositories.CarRepository;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

	@Mock private CarRepository carRepository;
	
	@InjectMocks
	private CarServiceImpl carService;
	
	@Test
	@DisplayName("Given a valid entity then save in database is success")
	void givenValidEntityThenSaveInDatabaseSuccess() {
		
		BrandEntity brand = new BrandEntity();
		brand.setName("test-brand");
		
		CarEntity entity = new CarEntity();
		entity.setBrand(brand);
		entity.setModel("test-model");
		entity.setOwner("test-owner");
		entity.setLicense("1111-T");
		
		when(carRepository.save(any(CarEntity.class))).thenReturn(entity);
		
		CarEntity result = carService.saveEntity(entity);
		
		assertNotNull(result);
		assertEquals(entity.getBrand(), result.getBrand());
		assertEquals(entity.getModel(), result.getModel());
		assertEquals(entity.getOwner(), result.getOwner());
		assertEquals(entity.getLicense(), result.getLicense());
		
		verify(carRepository, times(1)).save(any(CarEntity.class));
	}
	
	@Test
	@DisplayName("Given an invalid entity then throw error")
	void givenInvalidEntityThenThrowError() {
		BrandEntity brand = new BrandEntity();
		brand.setName("test-brand");
		
		CarEntity entity = new CarEntity();
		entity.setBrand(brand);
		entity.setOwner("test-owner");
		entity.setLicense("1111-T");
		
		ApiException exception = Assertions.assertThrows(ApiException.class, () -> carService.saveEntity(entity));
		
		assertNotNull(exception);
		assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
		assertEquals("Invalid car entity. Model is empty", exception.getMessage());
		
		verify(carRepository, times(0)).save(any(CarEntity.class));
	}	
}
