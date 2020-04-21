package fr.formation.inti.shop.api.service;

import com.inti.formation.shop.api.repository.model.Customer;

import fr.formation.inti.shop.api.repository.model.Price;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPriceService {

	Mono<Price> register(Price price);

    public Flux<Price> searchIdPrice(long idPrice) ;

    public Flux<Price> getPrices() ;

    public Mono<Price> update(Price p) ;

}
