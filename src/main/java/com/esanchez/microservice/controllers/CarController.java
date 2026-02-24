package com.esanchez.microservice.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esanchez.microservice.application.dto.CarDTO;
import com.esanchez.microservice.application.dto.ResponseDTO;
import com.esanchez.microservice.application.dto.mapping.CarMapping;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.services.CarService;
import com.esanchez.microservice.domain.model.CarEntity;


@RestController
@RequestMapping("/v1/api/cars")
public class CarController {

	private static final Logger logger = LogManager.getLogger(CarController.class);
	
	private final CarService carService;
	
	public CarController(CarService carService) {
		this.carService = carService;
	}
	
	@PostMapping
	public ResponseEntity<ResponseDTO> create(@RequestBody CarDTO car) {
		logger.info("Request create car: {}", car);
		
		try {
			CarEntity savedCar = carService.saveEntity(null);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(CarMapping.parseToDto(savedCar));
		} catch (ApiException e) {
			logger.error("Error saving entity in database. {}", e.getMessage());
			return ResponseEntity.status(e.getErrorCode()).body(new ResponseDTO(e.getErrorCode(), e.getMessage()));
		}
	}
	
	@GetMapping
	public ResponseEntity<List<CarDTO>> readAll() {
		logger.info("Request read all cars");
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CarDTO> read(@PathVariable Long id) {
		logger.info("Request read car with id {}", id);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CarDTO> update(@PathVariable Long id, @RequestBody CarDTO car) {
		logger.info("Request update car. id: {}, car: {}", id, car);
		
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CarDTO> partialUpdate(@PathVariable Long id, @RequestBody CarDTO car) {
		logger.info("Request partial update car. id: {}, car: {}", id, car);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info("Request delete car. id: {}", id);
		
		return ResponseEntity.noContent().build();
	}
}
