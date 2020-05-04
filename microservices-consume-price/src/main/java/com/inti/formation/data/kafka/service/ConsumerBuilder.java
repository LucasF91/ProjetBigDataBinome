package com.inti.formation.data.kafka.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.inti.formation.data.kafka.message.Price;

@Service
@Slf4j
public class ConsumerBuilder {

	@KafkaListener(topics = "${kafka.topic-name}", groupId = "${kafka.consumer-group-id}")
	public void consume(Price price) {
		log.info("Price readed " + price.toString() );
	}
}
