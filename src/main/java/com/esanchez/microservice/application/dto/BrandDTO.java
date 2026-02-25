package com.esanchez.microservice.application.dto;

public class BrandDTO extends BaseDTO {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BrandDTO [name=" + name + "]";
	}
}
