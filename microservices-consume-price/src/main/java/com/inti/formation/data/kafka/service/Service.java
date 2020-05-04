package com.inti.formation.data.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inti.formation.data.elastic.repository.PriceRepository;

@Component
public class Service {
    @Autowired(required=true)
    private PriceRepository priceRepository;

    void test() {
        priceRepository.findAll();
    }
}
