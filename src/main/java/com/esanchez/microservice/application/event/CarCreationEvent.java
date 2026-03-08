package com.esanchez.microservice.application.event;

public class CarCreationEvent {

	public Long id;
	
	public String brand, model;
	
	public CarCreationEvent(Builder builder) {
		this.id = builder.id;
		this.brand = builder.brand;
		this.model = builder.model;
	}
	
	public Long getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public static class Builder {
		private Long id;
		
		private String brand, model;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder brand(String brand) {
			this.brand = brand;
			return this;
		}
		
		public Builder model(String model) {
			this.model = model;
			return this;
		}
		
		public CarCreationEvent build() {
			return new CarCreationEvent(this);
		}
	}
}
