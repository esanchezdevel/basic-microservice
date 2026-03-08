package com.esanchez.microservice.infrastructure.messaging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.esanchez.microservice.application.exceptions.ApiException;
import com.esanchez.microservice.application.port.CarProducer;

@Service
public class KafkaProducer implements CarProducer<String, String> {

	private static final Logger logger = LogManager.getLogger(KafkaProducer.class);
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void produce(String topic, String event) throws ApiException {
		logger.info("Producing event to Kafka. Topic: {}, Event: {}", topic, event);
		
		try {
			kafkaTemplate.send(topic, event);
		} catch (Exception e) {
			String errorMessage = String.format("Error producing event to Kafka. %s", e.getMessage());
			logger.error(errorMessage);
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
		}
	};
}
