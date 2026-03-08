package com.esanchez.microservice.application.port;

import com.esanchez.microservice.application.exceptions.ApiException;

public interface CarProducer<K, V> {

	void produce(K topic, V event) throws ApiException;
}
