package com.esanchez.microservice.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class CarEntity {

	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private BrandEntity brand;
	
	private String model;
	
	private String owner;
	
	private String license;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BrandEntity getBrand() {
		return brand;
	}

	public void setBrand(BrandEntity brand) {
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
		return "CarEntity [id=" + id + ", brand=" + brand + ", model=" + model + ", owner=" + owner + ", license="
				+ license + "]";
	}
}
