package com.esanchez.microservice.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseDTO {

	@JsonInclude(Include.NON_EMPTY)
	private int responseCode;
	
	@JsonInclude(Include.NON_EMPTY)
	private String message;
	
	@JsonInclude(Include.NON_EMPTY)
	private BaseDTO body;
	
	public ResponseDTO() {
	}
	
	public ResponseDTO(Builder builder) {
		this.responseCode = builder.responseCode;
		this.message = builder.message;
		this.body = builder.body;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getMessage() {
		return message;
	}

	public BaseDTO getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "ResponseDTO [responseCode=" + responseCode + ", message=" + message + ", body=" + body + "]";
	}
	
	public static class Builder {
		private int responseCode;
		
		private String message;
		
		private BaseDTO body;
		
		public Builder responseCode(int responseCode) {
			this.responseCode = responseCode;
			return this;
		}
		
		public Builder message(String message) {
			this.message = message;
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
