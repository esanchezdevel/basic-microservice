package com.esanchez.microservice.application.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.impl.BrandServiceImpl;
import com.esanchez.microservice.domain.model.BrandEntity;
import com.esanchez.microservice.domain.repositories.BrandRepository;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

	@Mock private BrandRepository brandRepository;
	
	@InjectMocks
	private BrandServiceImpl brandService;
	
	@Test
	@DisplayName("Given a valid entity then save in database is success")
	void givenValidEntityThenSaveInDatabaseSuccess() {
		BrandEntity entity = new BrandEntity();
		entity.setName("test-brand");
		
		when(brandRepository.save(Mockito.any(BrandEntity.class))).thenReturn(entity);
		
		BrandEntity result = brandService.saveEntity(entity);
		
		assertNotNull(result);
		assertEquals(entity.getName(), result.getName());
	}
	
	@Test
	@DisplayName("Given an invalid entity then throw error")
	void givenInvalidEntityThenThrowError() {
		BrandEntity entity = new BrandEntity();
		
		ApiException exception = Assertions.assertThrows(ApiException.class, () -> brandService.saveEntity(entity));
		
		assertNotNull(exception);
		assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
		assertEquals("Invalid brand entity", exception.getMessage());
	}
}
