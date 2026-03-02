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
	
	public ResponseDTO(Builder builder) {
		this.responseCode = builder.responseCode;
		this.errorMessage = builder.errorMessage;
		this.body = builder.body;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public BaseDTO getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "ResponseDTO [responseCode=" + responseCode + ", errorMessage=" + errorMessage + ", body=" + body + "]";
	}
	
	public static class Builder {
		private int responseCode;
		
		private String errorMessage;
		
		private BaseDTO body;
		
		public Builder responseCode(int responseCode) {
			this.responseCode = responseCode;
			return this;
		}
		
		public Builder errorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
			return this;
		}
		
		public Builder body(BaseDTO body) {
			this.body = body;
			return this;
		}
		
		public ResponseDTO build() {
			return new ResponseDTO(this);
		}
	}
}
