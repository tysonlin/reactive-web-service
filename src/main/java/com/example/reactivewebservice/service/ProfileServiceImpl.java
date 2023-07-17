package com.example.reactivewebservice.service;

import com.example.reactivewebservice.error.custom.UserAlreadyExistException;
import com.example.reactivewebservice.error.custom.UserNotFoundException;
import com.example.reactivewebservice.model.Profile;
import com.example.reactivewebservice.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Mono<Profile> getOne(String id) {
        return profileRepository.findById(id)
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }

    @Override
    public Mono<Profile> putOne(Profile profile) {
        return isUserExistByEmail(profile.getEmail())
                .filter(exist -> !exist) // look for not exist
                .switchIfEmpty(Mono.error(UserAlreadyExistException::new))
                .flatMap(exist -> profileRepository.save(profile));
    }

    @Override
    public Mono<Void> deleteOne(String id) {
        return getOne(id)
                .map(profile -> profileRepository.deleteById(id))
                .then();
    }

    @Override
    public Flux<Profile> getAll() {
        return profileRepository.findAll();
    }

    private Mono<Boolean> isUserExistByEmail(String email) {
        return profileRepository.findByEmail(email)
                .map(user -> true)
                .switchIfEmpty(Mono.just(false));
    }
}
