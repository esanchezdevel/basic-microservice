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


@RestController
@RequestMapping("/v1/api")
public class ApiController {

	private static final Logger logger = LogManager.getLogger(ApiController.class);
	
	@PostMapping("/cars")
	public ResponseEntity<CarDTO> create(@RequestBody CarDTO car) {
		logger.info("Request create car: {}", car);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/cars")
	public ResponseEntity<List<CarDTO>> readAll() {
		logger.info("Request read all cars");
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/cars/{id}")
	public ResponseEntity<CarDTO> read(@PathVariable Long id) {
		logger.info("Request read car with id {}", id);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/cars/{id}")
	public ResponseEntity<CarDTO> update(@PathVariable Long id, @RequestBody CarDTO car) {
		logger.info("Request update car. id: {}, car: {}", id, car);
		
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/cars/{id}")
	public ResponseEntity<CarDTO> partialUpdate(@PathVariable Long id, @RequestBody CarDTO car) {
		logger.info("Request partial update car. id: {}, car: {}", id, car);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/cars/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info("Request delete car. id: {}", id);
		
		return ResponseEntity.noContent().build();
	}
}
