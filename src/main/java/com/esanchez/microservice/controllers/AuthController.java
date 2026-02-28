package com.esanchez.microservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esanchez.microservice.application.dto.ResponseDTO;
import com.esanchez.microservice.application.dto.JwtDTO;
import com.esanchez.microservice.application.dto.LoginRequestDTO;
import com.esanchez.microservice.application.security.JwtServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

	private final JwtServiceImpl jwtService;

	public AuthController(JwtServiceImpl jwtService) {
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO request) {

		if (!jwtService.checkAccessUser(request.getUsername(), request.getPassword())) {
			ResponseDTO response = new ResponseDTO();
			response.setResponseCode(HttpStatus.UNAUTHORIZED.value());
			response.setErrorMessage("Invalid User/Password.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
		
		String jwt = Jwts.builder()
							.setSubject(request.getUsername())
							.signWith(Keys.hmacShaKeyFor("test-secret-to-be-configured-somewhere-else".getBytes()))
							.compact();
		
		JwtDTO jwtDTO = new JwtDTO();
		jwtDTO.setToken(jwt);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setBody(jwtDTO);
		return ResponseEntity.ok(responseDTO);
	}
}
