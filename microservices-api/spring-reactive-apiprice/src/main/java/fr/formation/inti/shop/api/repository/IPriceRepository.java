package fr.formation.inti.shop.api.repository;

import fr.formation.inti.shop.api.repository.model.Price;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Date;


@Repository
public interface IPriceRepository extends ReactiveMongoRepository<Price, Long> {
    
    public Flux<Price> findByIdPrice(final long idPrice);

    @Query("{'$and':[ {'active':'true'}, {'date': {$gte: ?0}} ] }")
    public Flux<Price> findActivePriceDate(final Date date);

}
