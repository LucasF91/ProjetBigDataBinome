package fr.formation.inti.shop.api.price.producer;

import java.util.Random;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;


import fr.formation.inti.shop.api.repository.model.Price;
import reactor.core.publisher.Mono;

public class ProductBuilder {
	
	@Value("${kafka.topic-name}")
    private static String TOPIC;

    @Autowired
    private static KafkaTemplate<String, Price> kafkaTemplate;

    @Value("${kafka.compression-type}")
    private String compressionType;

    //milliseconds
//    @Scheduled(fixedDelayString = "${schedule-time}")
//    public void scheduleFixedDelayTask() {
//        Integer idPrice = new Random().ints(1,(1000+1)).findFirst().getAsInt();
//        Price event = new Price();
//        event.setCode( idPrice.toString());
//        event.setDate(new java.util.Date());
//        // Envoi de message
//        // param1 : nom du topic
//        // param2 : clé du message dans kafka
//        // param3 : value
//        ProducerRecord<String, Price> producerRecord = new ProducerRecord<>(TOPIC, event.getCode(),
//                event);
//	    kafkaTemplate.send(producerRecord);
//    }

	public static void kafkaDeleteTopic(Mono<Price> findByIdPrice) {
	        Integer idPrice = new Random().ints(1,(1000+1)).findFirst().getAsInt();
	        Price event = new Price();
//	        Float montant = (float) new Random().ints(1,(1000+1)).findFirst().getAsInt();
	        event.setCode( idPrice.toString());
	        event.setDate(new java.util.Date());
	        ProducerRecord<String, Price> producerRecord = new ProducerRecord<>(TOPIC, event.getCode(),
	                event);
		    kafkaTemplate.send(producerRecord);
	}

}
