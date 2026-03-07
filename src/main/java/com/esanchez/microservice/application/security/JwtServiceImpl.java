package com.esanchez.microservice.application.security;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	private final String SECRET = "test-secret-to-be-configured-somewhere-else";

	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public void validate(String token) throws Exception {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.build()
				.parseClaimsJws(token);
	}
}
