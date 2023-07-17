package com.example.reactivewebservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
public class ReactiveWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebServiceApplication.class, args);
	}

}
