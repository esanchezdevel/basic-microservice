package com.esanchez.microservice.application.security;

import com.esanchez.microservice.application.exceptions.ApiException;

public interface JwtService {

	String extractUsername(String token);
	
	boolean isValid(String token);
	
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
