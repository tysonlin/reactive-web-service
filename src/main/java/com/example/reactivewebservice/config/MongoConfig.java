package com.example.reactivewebservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@Profile("!test")
@Configuration
@EnableReactiveMongoAuditing
public class MongoConfig {
}
