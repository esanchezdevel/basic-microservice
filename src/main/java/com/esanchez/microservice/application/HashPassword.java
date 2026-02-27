package com.esanchez.microservice.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashPassword {
	
	private static final Logger logger = LogManager.getLogger(HashPassword.class); 
	
	public static void main(String[] args) {
		logger.info("Hashed password: {}", new BCryptPasswordEncoder().encode("write the password here"));
	}
}
