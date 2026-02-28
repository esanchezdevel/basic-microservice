package com.esanchez.microservice.application.dto;

import java.util.List;

public class CarReadAllResponseDTO extends BaseDTO {

	private List<CarDTO> cars;
	
	private int page;
	
	private int size;
	
	private long totalElements;
	
	private int totalPages;

	public List<CarDTO> getCars() {
		return cars;
	}

	public void setCars(List<CarDTO> cars) {
		this.cars = cars;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "CarReadAllResponseDTO [cars=" + cars + ", page=" + page + ", size=" + size + ", totalElements="
				+ totalElements + ", totalPages=" + totalPages + "]";
	}
}
