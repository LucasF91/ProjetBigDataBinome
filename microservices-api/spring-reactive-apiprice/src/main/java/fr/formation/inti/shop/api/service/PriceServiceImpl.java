package fr.formation.inti.shop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.formation.inti.shop.api.repository.PriceRepository;
import fr.formation.inti.shop.api.repository.model.Price;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PriceServiceImpl implements IPriceService {
    @Autowired
    private PriceRepository priceRepository;

	@Override
	public Mono<Price> register(Price price) {
		return priceRepository.save(price);
	}

	
	public Flux<Price> searchCode(final String code) {
		return priceRepository.findByCode(code);
	}

	@Override
	public Flux<Price> getPrices() {
		return priceRepository.findAll();
	}

	@Override
	public Mono<Price> update(Price price) {
		return priceRepository.save(price);
	}

	@Override
	public Mono<Void> deletePrice(Price price) {
		return priceRepository.delete(price);
	}



   

}