package com.esanchez.microservice.application.dto.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.esanchez.microservice.application.dto.CarDTO;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.domain.model.BrandEntity;
import com.esanchez.microservice.domain.model.CarEntity;
import com.esanchez.microservice.domain.repositories.BrandRepository;

@ExtendWith(MockitoExtension.class)
public class CarMappingTest {

	@Mock private BrandRepository brandRepositoryMock;
	
	@InjectMocks
	private CarMapping carMapping;
	
	@Test
	@DisplayName("Given one entity Then parse to DTO")
	void givenEntityThenParseToDTO() {
		
		CarEntity carEntity = buildCarEntity(1L, "test-brand", 1L, "test-model", "test-owner", "1111-T");
		
		CarDTO result = carMapping.parseToDto(carEntity);
		
		assertNotNull(result);
		assertEquals("1", result.getId());
		assertEquals("test-brand", result.getBrand());
		assertEquals("test-model", result.getModel());
		assertEquals("test-owner", result.getOwner());
		assertEquals("1111-T", result.getLicense());
	}
	
	@Test
	@DisplayName("Given two entity Then parse to DTO list")
	void givenTwoEntitiesThenParseToDTOList() {
		
		CarEntity carEntity1 = buildCarEntity(1L, "test-brand1", 1L, "test-model1", "test-owner1", "1111-T");
		CarEntity carEntity2 = buildCarEntity(2L, "test-brand2", 2L, "test-model2", "test-owner2", "1111-X");
		
		List<CarDTO> result = carMapping.parseToDtoList(List.of(carEntity1, carEntity2));
		
		assertNotNull(result);
		assertEquals(2, result.size());
	}
	
	@Test
	@DisplayName("Given one DTO Then parse to Entity")
	void givenDTOThenParseToEntity() {
		
		CarDTO carDTO = buildCarDTO("1", "test-brand", "test-model", "test-owner", "1111-T");
		
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(1L);
		brandEntity.setName("test-brand");
		
		when(brandRepositoryMock.findByName(anyString())).thenReturn(Optional.of(brandEntity));
		
		CarEntity result = carMapping.parseToEntity(carDTO);
		
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("test-brand", result.getBrand().getName());
		assertEquals("test-model", result.getModel());
		assertEquals("test-owner", result.getOwner());
		assertEquals("1111-T", result.getLicense());
	}
	
	@Test
	@DisplayName("Given one DTO When brand is not found Then throw error")
	void givenDTOWhenBrandNotFoundThenThrowError() {
		
		CarDTO carDTO = buildCarDTO("1", "test-brand", "test-model", "test-owner", "1111-T");
		
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(1L);
		brandEntity.setName("test-brand");
		
		when(brandRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());
		
		ApiException exception = Assertions.assertThrows(ApiException.class, () -> carMapping.parseToEntity(carDTO));
		
		assertNotNull(exception);
		assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
		assertEquals("Brand '" + carDTO.getBrand() + "' not found in database", exception.getMessage());
	}
	
	private CarDTO buildCarDTO (String id, String brand, String model, String owner, String license) {
		CarDTO carDTO = new CarDTO();
		carDTO.setId(id);
		carDTO.setBrand(brand);
		carDTO.setModel(model);
		carDTO.setOwner(owner);
		carDTO.setLicense(license);
		
		return carDTO;
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
