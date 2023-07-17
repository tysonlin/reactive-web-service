package com.example.reactivewebservice.controller;

import com.example.reactivewebservice.config.MockProfiles;
import com.example.reactivewebservice.error.custom.UserAlreadyExistException;
import com.example.reactivewebservice.error.custom.UserNotFoundException;
import com.example.reactivewebservice.model.Profile;
import com.example.reactivewebservice.service.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers  = ProfileController.class)
@ActiveProfiles(profiles = {"test"})
class ProfileControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProfileServiceImpl profileService;

    @Test
    void getAll() {
        when(profileService.getAll()).thenReturn(Flux.just(MockProfiles.mockProfile));

        webTestClient.get()
                .uri("/profiles")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Profile.class)
                .contains(MockProfiles.mockProfile);
    }

    @Test
    void getOne() {
        when(profileService.getOne(any())).thenReturn(Mono.just(MockProfiles.mockProfile));

        webTestClient.get()
                .uri("/profiles/123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Profile.class);
    }

    @Test
    void getOneNotFound() {
        when(profileService.getOne(any())).thenReturn(Mono.error(new UserNotFoundException()));

        webTestClient.get()
                .uri("/profiles/123")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void putOne() {
        when(profileService.putOne(any())).thenReturn(Mono.just(MockProfiles.mockProfile));

        webTestClient.put()
                .uri("/profiles")
                .body(BodyInserters.fromValue(MockProfiles.mockProfile))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Profile.class);
    }

    @Test
    void putOneUserExists() {
        when(profileService.putOne(any())).thenReturn(Mono.error(new UserAlreadyExistException()));

        webTestClient.put()
                .uri("/profiles")
                .body(BodyInserters.fromValue(MockProfiles.mockProfile))
                .exchange()
                .expectStatus().isBadRequest();
    }
}
