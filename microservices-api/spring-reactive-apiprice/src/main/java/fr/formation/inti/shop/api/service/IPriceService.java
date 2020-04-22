package fr.formation.inti.shop.api.service;

import fr.formation.inti.shop.api.repository.model.Price;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPriceService {

	public Mono<Price> register(Price price);

    public Flux<Price> searchCode(String code) ;

    public Flux<Price> getPrices() ;

    public Mono<Price> update(Price price) ;

	public Mono<Void> deletePrice(Price price);
}
