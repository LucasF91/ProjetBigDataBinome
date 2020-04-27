package fr.formation.inti;


import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

import java.sql.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.formation.inti.shop.api.rest.exception.InternalServerException;
import fr.formation.inti.shop.api.price.producer.ProductBuilder;
import fr.formation.inti.shop.api.repository.model.Price;
import fr.formation.inti.shop.api.rest.exception.ValidationParameterException;
import fr.formation.inti.shop.api.service.IPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/price")
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

    @PostMapping(value = "/register" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Price is registered" )
    public Mono<String> register(@RequestBody Price price) {
        // Vérification des paramètres
        if( ObjectUtils.anyNotNull(price)  && !ObjectUtils.allNotNull(price.getIdPrice(), price.isActive(), price.getMontant(), price.getCode(), price.getDate() )){
            log.error("Validation error: one of attributes is not found");
            return Mono.error(new ValidationParameterException("(Validation error message): one of attributes is not found" ));
        }
        return Mono.just(price)
        .map(data->
                {

                    return priceService.register(data).subscribe().toString();

                });
    }
    @GetMapping
    @RequestMapping(value = "/findCode/")
    public Flux<Price> findByCode(
            @RequestParam(value = "code") String code)
    {
        log.debug("usecode", code);
        return priceService.findByCode(code)
                .doOnNext(data -> log.info(data.getCode() + " is found"));
    }
    
    @GetMapping
    @RequestMapping(value = "/findActivePriceDate")
    public Flux<Price> findActivePriceDate(
            @RequestParam(name = "dateselect")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    Date date) {
        log.debug("use date", date);
        return priceService.findActivePriceDate(date)
                .doOnNext(data -> log.info(data.getDate() + " is found"));
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
    
    @PutMapping(value = "/updatePrice", headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus(value =  HttpStatus.OK, reason = "Update of Price")
    public Mono<String> updateStock(@RequestBody Price price){
        if(ObjectUtils.anyNotNull(price) && !ObjectUtils.anyNotNull(price.getCode(),
                price.getIdPrice(),
                price.getMontant(),
                price.isActive(),
                price.getCode(),
                price.getDate() )) {
            log.error("Error on update: Values of price isn't correct");
            return Mono.error(new ValidationParameterException("Price params exception"));
        }
        return Mono.just(price)
                .map(data -> priceService.updatePrice(data).subscribe().toString());
    }

    @DeleteMapping
    @RequestMapping(value = "/deleteprice")
    @ResponseStatus(value =  HttpStatus.OK, reason = "Delete of Price")
    public Mono<Price> deleteprice(@RequestParam(name ="idPrice") String IdPrice) {
    	ProductBuilder.kafkaDeleteTopic(priceService.findByIdPrice(Long.parseLong(IdPrice)));
    	return priceService.deletePrice(IdPrice);
    }
	
}
