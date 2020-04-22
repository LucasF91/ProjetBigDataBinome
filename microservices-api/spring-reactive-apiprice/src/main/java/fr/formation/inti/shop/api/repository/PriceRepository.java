package fr.formation.inti.shop.api.repository;

import fr.formation.inti.shop.api.repository.model.Price;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Date;


@Repository
public interface PriceRepository extends ReactiveMongoRepository<Price, Long> {
    
    Flux<Price> findByIdPrice(final long idPrice);

//    Flux<Price> searchCode(final String code);

    @Query("{'$and':[ {'active':'true'}, {'date': {$gte: ?0}} ] }")
    Flux<Price> searchActiveAndDateSelect(final boolean active, final Date date);

    Flux<Price> findByDateBetween( final Date date1,  final Date date2);

    Flux<Price> findByMontantBetween( final float montant1,  final Date montant2);
}
