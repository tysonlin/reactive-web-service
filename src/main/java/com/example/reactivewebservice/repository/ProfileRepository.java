package com.example.reactivewebservice.repository;

import com.example.reactivewebservice.model.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {

    Mono<Profile> findByEmail(final String email);
}
