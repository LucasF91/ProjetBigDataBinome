package com.inti.formation.data.kafka.service;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.inti.formation.data.elastic.model.Prix;
import com.inti.formation.data.elastic.repository.PriceRepository;
import com.inti.formation.data.kafka.message.Price;

@Component
@Service
@Slf4j
public class ConsumerBuilder {
	
	@Autowired
	@Qualifier("repoprice")
	PriceRepository repo;

	@KafkaListener(topics = "${kafka.topic-name}", groupId = "${kafka.consumer-group-id}")
	public void consume(Price price) {
		log.info("Price readed " + price.toString() );
		Prix  prix = new Prix();
		prix.setIdPrice(Long.valueOf(price.getIdPrice()));
		prix.setMontant(price.getMontant());
		prix.setActive(price.isActive());
		prix.setCode(price.getCode());
		prix.setDate(price.getDate());
		prix.setDateSup(price.getDateSup());
		repo.save(prix);
	}
}
