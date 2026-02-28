package com.esanchez.microservice.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseDTO {

	@JsonInclude(Include.NON_EMPTY)
	private int responseCode;
	
	@JsonInclude(Include.NON_EMPTY)
	private String errorMessage;
	
	@JsonInclude(Include.NON_EMPTY)
	private BaseDTO body;
	
	public ResponseDTO() {
	}
	
	public ResponseDTO(int errorCode, String errorMessage) {
		super();
		this.responseCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int errorCode) {
		this.responseCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public BaseDTO getBody() {
		return body;
	}

	public void setBody(BaseDTO body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ResponseDTO [responseCode=" + responseCode + ", errorMessage=" + errorMessage + ", body=" + body + "]";
	}
}
