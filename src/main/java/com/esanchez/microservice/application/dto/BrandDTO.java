package com.esanchez.microservice.application.dto;

public class BrandDTO extends BaseDTO {

	private String id;
	
	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BrandDTO [id=" + id + ", name=" + name + "]";
	}
}
