package com.esanchez.microservice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esanchez.microservice.application.dto.BrandDTO;
import com.esanchez.microservice.application.dto.mapping.Mapping;
import com.esanchez.microservice.application.dto.BaseDTO;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.BrandService;
import com.esanchez.microservice.domain.model.BrandEntity;

@RestController
@RequestMapping("/v1/api/brands")
public class BrandController {

	private static final Logger logger = LogManager.getLogger(BrandController.class);
	
	private final BrandService brandService;
	
	private final Mapping<BrandDTO, BrandEntity> brandMapping;
	
	public BrandController(BrandService brandService, Mapping<BrandDTO, BrandEntity> brandMapping) {
		this.brandService = brandService;
		this.brandMapping = brandMapping;
	}
	
	@PostMapping
	public ResponseEntity<BaseDTO> create(@RequestBody BrandDTO brand) {
		logger.info("Request create brand: {}", brand);
		
		try {
			BrandEntity savedBrand = brandService.saveEntity(brandMapping.parseToEntity(brand));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(brandMapping.parseToDto(savedBrand));
		} catch (ApiException e) {
			logger.error("Error saving entity in database. {}", e.getMessage());
			return ResponseEntity.status(e.getErrorCode()).body(new BaseDTO(e.getErrorCode(), e.getMessage()));
		}
	}
}
