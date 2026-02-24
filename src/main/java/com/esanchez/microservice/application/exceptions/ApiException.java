package com.esanchez.microservice.application.exceptions;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	
	public ApiException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		return "ApiException [errorCode=" + errorCode + ", errorMessage=" + super.getMessage() + "]";
	}
}
