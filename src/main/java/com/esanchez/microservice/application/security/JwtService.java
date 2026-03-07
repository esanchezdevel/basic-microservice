package com.esanchez.microservice.application.security;

public interface JwtService {

	String extractUsername(String token);
	
	/**
	 * Validate if one JWT is valid.
	 * If the token is not valid and Exception will be raised
	 * 
	 * @param token The JWT to be validated.
	 */
	void validate(String token) throws Exception;
}
