package com.esanchez.microservice.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

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
	
	/*
	 * Tests save method
	 */
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
	
	/*
	 * Tests getAllEntities method
	 */
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
		
		when(carRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException("Unexpected error"));

		ApiException exception = assertThrows(ApiException.class, () -> carService.getAllEntities(PageRequest.of(0, 10)));
		
		assertNotNull(exception);
		assertEquals("Unexpected error getting all entities from database. Unexpected error", exception.getMessage());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getErrorCode());
		
		verify(carRepository, times(1)).findAll(any(Pageable.class));
	}
	
	/*
	 * Tests getEntity method
	 */
	@Test
	@DisplayName("Given one id When car is present in database Then return entity")
	void givenOneIdWhenCarIsPresentInDatabaseThenReturnEntity() {
	
		CarEntity expectedCarEntity = buildCarEntity(1L, "test-name", 1L, "test-model", "test-owner", "1111-T");
		
		when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedCarEntity));
		
		Optional<CarEntity> result = carService.getEntity(1L);
		
		assertNotNull(result);
		assertTrue(result.isPresent());
		assertEquals(1L, result.get().getId());
	}
	
	@Test
	@DisplayName("Given one id When car is not present in database Then return Empty")
	void givenOneIdWhenCarIsNotPresentInDatabaseThenReturnEmpty() {
	
		when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		Optional<CarEntity> result = carService.getEntity(1L);
		
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
	
	@Test
	@DisplayName("Given one id When error happens Then throw exception")
	void givenOneIdWhenErrorHappensThenThrowException() {
	
		when(carRepository.findById(any(Long.class))).thenThrow(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error"));
		
		ApiException exception = assertThrows(ApiException.class, () -> carService.getEntity(1L));
		
		assertNotNull(exception);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getErrorCode());
		assertEquals("Unexpected error", exception.getMessage());
	}
	
	/*
	 * Tests updateEntity method
	 */
	@Test
	@DisplayName("Given one id When car is present in database Then update")
	void givenOneIdWhenCarIsPresentInDatabaseThenUpdate() {
		
		CarEntity carEntity = buildCarEntity(1L, "test-name2", 1L, "test-model2", "test-owner2", "1111-X");
		CarEntity dbCarEntity = buildCarEntity(1L, "test-name", 1L, "test-model", "test-owner", "1111-T");
		
		when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(dbCarEntity));
		
		CarEntity updatedCarEntity = carService.updateEntity(1L, carEntity);
		
		assertNotNull(updatedCarEntity);
		assertEquals("test-name2", updatedCarEntity.getBrand().getName());
		assertEquals("test-model2", updatedCarEntity.getModel());
		assertEquals("test-owner2", updatedCarEntity.getOwner());
		assertEquals("1111-X", updatedCarEntity.getLicense());
	}

	@Test
	@DisplayName("Given one id When car is NOT present in database Then throw error")
	void givenOneIdWhenCarIsNotPresentInDatabaseThenThrowError() {
		
		CarEntity carEntity = buildCarEntity(1L, "test-name2", 1L, "test-model2", "test-owner2", "1111-X");
		
		when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		ApiException exception = assertThrows(ApiException.class, () -> carService.updateEntity(1L, carEntity));
		
		assertNotNull(exception);
		assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
		assertEquals("Car not found in database", exception.getMessage());
	}
	
	/*
	 * Tests partialUpdateEntity method
	 */
	@Test
	@DisplayName("Given one id When car is present in database Then partial update")
	void givenOneIdWhenCarIsPresentInDatabaseThenPartialUpdate() {
		
		CarEntity carEntity = new CarEntity();
		carEntity.setLicense("1111-X");
		CarEntity dbCarEntity = buildCarEntity(1L, "test-name", 1L, "test-model", "test-owner", "1111-T");
		
		when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(dbCarEntity));
		
		CarEntity updatedCarEntity = carService.partialUpdateEntity(1L, carEntity);
		
		assertNotNull(updatedCarEntity);
		assertEquals("test-name", updatedCarEntity.getBrand().getName());
		assertEquals("test-model", updatedCarEntity.getModel());
		assertEquals("test-owner", updatedCarEntity.getOwner());
		assertEquals("1111-X", updatedCarEntity.getLicense());
	}

	@Test
	@DisplayName("Given one id When car is NOT present in partial update Then throw error")
	void givenOneIdWhenCarIsNotPresentInPartialUpdateThenThrowError() {
		
		CarEntity carEntity = new CarEntity();
		carEntity.setLicense("1111-X");
		
		when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		ApiException exception = assertThrows(ApiException.class, () -> carService.partialUpdateEntity(1L, carEntity));
		
		assertNotNull(exception);
		assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
		assertEquals("Car not found in database", exception.getMessage());
	}
	
	/*
	 * Tests deleteEntity method
	 */
	@Test
	@DisplayName("Given one id When car is present in database Then delete")
	void givenOneIdWhenCarIsPresentInDatabaseThenDelete() {
		
		doNothing().when(carRepository).deleteById(any(Long.class));
		
		carService.deleteEntity(1L);
		
		verify(carRepository, times(1)).deleteById(any(Long.class));
	}

	@Test
	@DisplayName("Given one id When error happens Then throw error")
	void givenOneIdWhenErrorHappensThenThrowError() {
		
		doThrow(new RuntimeException("Unexpected error")).when(carRepository).deleteById(any(Long.class));
		
		ApiException exception = assertThrows(ApiException.class, () -> carService.deleteEntity(1L));
		
		assertNotNull(exception);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getErrorCode());
		assertEquals("Unexpected error", exception.getMessage());
	}
	
	private CarEntity buildCarEntity(long brandId, String brandName, long carId, String carModel, String carOwner, String carLicense) {
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(brandId);
		brandEntity.setName(brandName);
		
		CarEntity carEntity = new CarEntity();
		carEntity.setId(carId);
		carEntity.setBrand(brandEntity);
		carEntity.setModel(carModel);
		carEntity.setOwner(carOwner);
		carEntity.setLicense(carLicense);
		
		return carEntity;
	}
}
