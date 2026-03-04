package com.esanchez.microservice.application.security;

import com.esanchez.microservice.application.exceptions.ApiException;

public interface JwtService {

	String extractUsername(String token);
	
	/**
	 * Validate if one JWT is valid.
	 * If the token is not valid and Exception will be raised
	 * 
	 * @param token The JWT to be validated.
	 */
	void validate(String token) throws Exception;
	
	/**
	 * Check if one user is present in database 
	 * 
	 * @param username The user name
	 * @param password The Hash password
	 * @return true if it's present. false if it's not present
	 * @throws ApiException 
	 */
	boolean checkAccessUser(String username, String password) throws ApiException;
}
