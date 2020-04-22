package fr.formation.inti;


import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.formation.inti.shop.api.rest.exception.InternalServerException;

import fr.formation.inti.shop.api.repository.model.Price;
import fr.formation.inti.shop.api.rest.bean.PriceRequest;
import fr.formation.inti.shop.api.rest.exception.ValidationParameterException;
import fr.formation.inti.shop.api.service.IPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/shop")
@Slf4j
public class EndPoint {

	@Autowired
    IPriceService priceService;

    @ExceptionHandler(ValidationParameterException.class)
    public Mono<ResponseEntity<String>> handlerValidationParameterException(ValidationParameterException e) {
     return Mono.just(
                badRequest().body("Missing parameter: "+ e.getMessage()));
    }

    @ExceptionHandler(InternalServerException.class)
    public Mono<ResponseEntity<String>> handlerInternalServerException() {
        return Mono.just(status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error server has occurred "));
    }

    @PostMapping(value = "/register/price" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Price is registered" )
    public Mono<String> create(@RequestBody PriceRequest price) {
        // Vérification des paramètres
        if( ObjectUtils.anyNotNull(price)  && !ObjectUtils.allNotNull(price.getIdPrice(),price.getMontant(), price.getCode(), price.getDate() )){
            log.error("Validation error: one of attributes is not found");
            return Mono.error(new ValidationParameterException("(Validation error message): one of attributes is not found" ));
        }
        return Mono.just(price)
        .map(data->
                {

                    return priceService.register( data).subscribe().toString();

                });
    }

    @GetMapping
    @RequestMapping(value = "/prices{pricename}")

    
    public Flux<Price> getPrices(@RequestParam(required = true, name = "pricename") long pricename ) {
        log.info("Searching  {} ",pricename );
        return priceService.searchIdPrice(pricename)

                // uses of doNext

                .doOnNext(price -> log.info(price.getIdPrice()+ " is found"));

    }



    @GetMapping
    @RequestMapping(value = "/prices/")
    public Flux<Price> getPrices() {
        log.info("All prices searching");
      return priceService.getPrices()
              // uses of map
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map( data-> data);
    }
	
	
}
