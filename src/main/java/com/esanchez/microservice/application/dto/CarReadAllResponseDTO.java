package com.esanchez.microservice.application.dto;

import java.util.List;

public class CarReadAllResponseDTO extends BaseDTO {

	private List<CarDTO> cars;
	
	private int page;
	
	private int size;
	
	private long totalElements;
	
	private int totalPages;
	
	public CarReadAllResponseDTO (Builder builder) {
		this.cars = builder.cars;
		this.page = builder.page;
		this.size = builder.size;
		this.totalElements = builder.totalElements;
		this.totalPages = builder.totalPages;
	}

	public List<CarDTO> getCars() {
		return cars;
	}
	
	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public String toString() {
		return "CarReadAllResponseDTO [cars=" + cars + ", page=" + page + ", size=" + size + ", totalElements="
				+ totalElements + ", totalPages=" + totalPages + "]";
	}
	
	public static class Builder {
		private List<CarDTO> cars;
		
		private int page, size, totalPages;
		
		private long totalElements;
		
		public Builder() {
		}
		
		public Builder cars(List<CarDTO> cars) {
			this.cars = cars;
			return this;
		}
		
		public Builder page(int page) {
			this.page = page;
			return this;
		}
		
		public Builder size(int size) {
			this.size = size;
			return this;
		}
		
		public Builder totalElements(long totalElements) {
			this.totalElements = totalElements;
			return this;
		}
		
		public Builder totalPages(int totalPages) {
			this.totalPages = totalPages;
			return this;
		}
		
		public CarReadAllResponseDTO build() {
			return new CarReadAllResponseDTO(this);
		}
	}
}
