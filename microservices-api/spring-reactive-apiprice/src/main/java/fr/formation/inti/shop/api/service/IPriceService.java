package fr.formation.inti.shop.api.service;

import java.util.Date;

import fr.formation.inti.shop.api.repository.model.Price;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPriceService {

	
	Mono<Price> register(Price price);

	public Flux<Price> findByCode(final String code) ;
	
	public Mono<Price> findByIdPrice(final long idPrice);
	 
	public Mono<Price> savePrice(Price price);
	
	public Flux<Price> findActivePriceDate(Date date);

    public Flux<Price> getPrices() ;

    public Mono<Price> updatePrice(Price price) ;
    
    public Mono<Void> deletePrice(Price price) ;
    
    public Mono<Price> deleteIdPrice(String idPrice);

}
