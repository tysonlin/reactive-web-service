package com.example.reactivewebservice.service;

import com.example.reactivewebservice.model.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Mono<Profile> getOne(String id);

    Mono<Profile> putOne(Profile profile);

    Mono<Void> deleteOne(String id);

    Flux<Profile> getAll();
}
