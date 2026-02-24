package com.esanchez.microservice.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseDTO {

	@JsonInclude(Include.NON_EMPTY)
	private int errorCode;
	
	@JsonInclude(Include.NON_EMPTY)
	private String errorMessage;
	
	public ResponseDTO(int errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ResponseDTO [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}
}
