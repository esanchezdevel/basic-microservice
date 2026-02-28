package com.esanchez.microservice.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	@Test
	@DisplayName("Given a Pageable then returns Page")
	void givenPageableThenReturnPage() {
		
		BrandEntity brand = new BrandEntity();
		brand.setId(1L);
		brand.setName("test-brand");
		
		CarEntity car = new CarEntity();
		car.setId(1L);
		car.setBrand(brand);
		car.setModel("test-model");
		car.setOwner("test-owner");
		car.setLicense("1111-T");
		
		List<CarEntity> cars = List.of(car);
		
		Page<CarEntity> page = new PageImpl<CarEntity>(cars);
		
		when(carRepository.findAll(any(Pageable.class))).thenReturn(page);

		Page<CarEntity> result = carService.getAllEntities(PageRequest.of(0, 10));
		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		
		verify(carRepository, times(1)).findAll(any(Pageable.class));
	}
	
	@Test
	@DisplayName("Given a Pageable then throws unexpected error")
	void givenPageableThenThrowsUnexpectedError() {
		
		BrandEntity brand = new BrandEntity();
		brand.setId(1L);
		brand.setName("test-brand");
		
		CarEntity car = new CarEntity();
		car.setId(1L);
		car.setBrand(brand);
		car.setModel("test-model");
		car.setOwner("test-owner");
		car.setLicense("1111-T");
		
		List<CarEntity> cars = List.of(car);
		
		Page<CarEntity> page = new PageImpl<CarEntity>(cars);
		
		when(carRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException("Unexpected error"));

		ApiException exception = assertThrows(ApiException.class, () -> carService.getAllEntities(PageRequest.of(0, 10)));
		
		assertNotNull(exception);
		assertEquals("Unexpected error getting all entities from database. Unexpected error", exception.getMessage());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getErrorCode());
		
		verify(carRepository, times(1)).findAll(any(Pageable.class));
	}
}
