package com.esanchez.microservice.application.dto;

public class CarDTO extends BaseDTO {

	private String brand;
	
	private String model;
	
	private String owner;
	
	private String license;

	public CarDTO() {
		super();
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Override
	public String toString() {
		return "CarDTO [brand=" + brand + ", model=" + model + ", owner=" + owner + ", license=" + license + "]";
	}
}
