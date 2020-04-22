package fr.formation.inti;


import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public Mono<String> register(@RequestBody Price price) {
        // Vérification des paramètres
        if( ObjectUtils.anyNotNull(price)  && !ObjectUtils.allNotNull(price.getIdPrice(),price.getMontant(), price.isActive(), price.getCode(), price.getDate() )){
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
    @RequestMapping(value = "/prices{code}")

    
    public Flux<Price> getPrices(@RequestParam(required = true, name = "code") String code ) {
        log.info("Searching  {} ",code );
        return priceService.searchCode(code)

                // uses of doNext

                .doOnNext(price -> log.info(price.getCode()+ " is found"));

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
    
    @PutMapping(value = "/updateprice/" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.OK, reason="Price is update" )
    public Mono<String> update(@RequestBody Price price) {
        // Vérification des paramètres
        if( ObjectUtils.anyNotNull(price)  && !ObjectUtils.allNotNull(price.getIdPrice(),price.getMontant(), price.isActive(), price.getCode(), price.getDate() )){
            log.error("Update error: one of attributes is not found");
            return Mono.error(new ValidationParameterException("(Price error message): one of attributes is not found" ));
        }
        return Mono.just(price)
        .map(data->
                {

                    return priceService.update( data).subscribe().toString();

                });
    }
    
    
    @DeleteMapping
    @RequestMapping(value = "/deleteprice")
    public Mono<Void> deleteprice(@RequestBody Price price ) {
    	return priceService.deletePrice(price);
    }
    
	
}
