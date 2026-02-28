package com.esanchez.microservice.application.dto.mapping;

import java.util.List;

import com.esanchez.microservice.application.dto.BaseDTO;
import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.domain.model.BaseEntity;

public interface Mapping<T extends BaseDTO, K extends BaseEntity> {

	T parseToDto(K entity);
	
	List<T> parseToDtoList(List<K> entities);
	
	K parseToEntity(T dto) throws ApiException;
}
