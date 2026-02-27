package com.esanchez.microservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esanchez.microservice.application.dto.LoginRequestDTO;
import com.esanchez.microservice.application.security.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

	private final JwtService jwtService;

	public AuthController(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequestDTO request) {

		// TODO validate username/password here

		return Jwts.builder()
				.setSubject(request.getUsername())
				.signWith(Keys.hmacShaKeyFor("test-secret-to-be-configured-somewhere-else".getBytes()))
				.compact();
	}
}
