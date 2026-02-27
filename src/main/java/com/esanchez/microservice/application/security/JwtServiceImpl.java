package com.esanchez.microservice.application.security;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.domain.model.AppUserEntity;
import com.esanchez.microservice.domain.repositories.AppUserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	private final String SECRET = "test-secret-to-be-configured-somewhere-else";

	private final AppUserRepository appUserRepository;
	
	public JwtServiceImpl(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public boolean isValid(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean checkAccessUser(String username, String password) throws ApiException {
		boolean result = false;
		
		Optional<AppUserEntity> appUser = appUserRepository.findByUsername(username);
		
		if (!appUser.isEmpty() && BCrypt.checkpw(password, appUser.get().getPassword()))
			result = true;
		
		return result;
	}
}
