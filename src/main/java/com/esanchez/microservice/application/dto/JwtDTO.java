package com.esanchez.microservice.application.dto;

public class JwtDTO extends BaseDTO {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
