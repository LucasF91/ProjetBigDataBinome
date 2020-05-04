package com.inti.formation.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
@ComponentScan

public class ApplicationConsumer {


	public static void main(String[] args) {
		SpringApplication.run(ApplicationConsumer.class, args);
	}
}
