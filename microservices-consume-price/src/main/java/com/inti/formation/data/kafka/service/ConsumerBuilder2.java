package com.inti.formation.data.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inti.formation.data.elastic.model.PriceDevice;
import com.inti.formation.data.elastic.repository.PriceRepository;


@Component
@Slf4j
public class ConsumerBuilder2 {

@Autowired
PriceRepository repo;

	public void consume(PriceDevice price) {
		log.info("Price readed " + price.toString() );
		PriceDevice  device = new PriceDevice();
		device.setIdPrice(Long.valueOf(price.getIdPrice()));
		device.setMontant(price.getMontant());
		device.setActive(price.isActive());
		device.setCode(price.getCode());
		device.setDate(price.getDate());
		repo.save(device);
	}
}
