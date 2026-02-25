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

import com.esanchez.microservice.application.dto.BaseDTO;
import com.esanchez.microservice.application.dto.BrandDTO;
import com.esanchez.microservice.application.dto.mapping.Mapping;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.BrandService;
import com.esanchez.microservice.domain.model.BrandEntity;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {

	@Mock private BrandService brandServiceMock;
	@Mock private Mapping<BrandDTO, BrandEntity> brandMappingMock;
	
	@InjectMocks
	private BrandController brandController;
	
	/**
	 * Tests POST create car
	 */
	@Test
	@DisplayName("Given a valid entity then success")
	void givenValidEntityWhenSaveThenReturnSuccess() {
		
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(1L);
		brandEntity.setName("test-brand");
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("test-brand");
		
		when(brandMappingMock.parseToEntity(any(BrandDTO.class))).thenReturn(brandEntity);
		when(brandServiceMock.saveEntity(any(BrandEntity.class))).thenReturn(brandEntity);
		when(brandMappingMock.parseToDto(any(BrandEntity.class))).thenReturn(brandDTO);
		
		ResponseEntity<BaseDTO> response = brandController.create(brandDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(brandDTO.toString(), response.getBody().toString());
		
		verify(brandMappingMock, times(1)).parseToEntity(any(BrandDTO.class));
		verify(brandServiceMock, times(1)).saveEntity(any(BrandEntity.class));
		verify(brandMappingMock, times(1)).parseToDto(any(BrandEntity.class));
	}
	
	@Test
	@DisplayName("Given an invalid entity then error")
	void givenInvalidEntityWhenSaveThenReturnError() {
		
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setId(1L);
		brandEntity.setName("test-brand");
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("test-brand");
		
		BaseDTO expectedResponse = new BaseDTO(HttpStatus.BAD_REQUEST.value(), "Unexpected error");
		
		when(brandMappingMock.parseToEntity(any(BrandDTO.class))).thenReturn(brandEntity);
		when(brandServiceMock.saveEntity(any(BrandEntity.class))).thenThrow(new ApiException(HttpStatus.BAD_REQUEST.value(), "Unexpected error"));
		
		ResponseEntity<BaseDTO> response = brandController.create(brandDTO);
		
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(expectedResponse.toString(), response.getBody().toString());
		
		verify(brandMappingMock, times(1)).parseToEntity(any(BrandDTO.class));
		verify(brandServiceMock, times(1)).saveEntity(any(BrandEntity.class));
		verify(brandMappingMock, times(0)).parseToDto(any(BrandEntity.class));
	}
}
