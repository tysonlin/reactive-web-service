package com.example.reactivewebservice.service;

import com.example.reactivewebservice.config.MockProfiles;
import com.example.reactivewebservice.error.custom.UserAlreadyExistException;
import com.example.reactivewebservice.error.custom.UserNotFoundException;
import com.example.reactivewebservice.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Test
    void getAll() {
        when(profileRepository.findAll()).thenReturn(Flux.just(MockProfiles.mockProfile));

        StepVerifier
                .create(profileService.getAll())
                .consumeNextWith(profile -> {
                    assertEquals("Unit", profile.getFirstName());
                    assertEquals("Test", profile.getLastName());
                    assertEquals("unit@test.io", profile.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void getOne() {
        when(profileRepository.findById(anyString())).thenReturn(Mono.just(MockProfiles.mockProfile));

        StepVerifier
                .create(profileService.getOne("id"))
                .consumeNextWith(profile -> {
                    assertEquals("Unit", profile.getFirstName());
                    assertEquals("Test", profile.getLastName());
                    assertEquals("unit@test.io", profile.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void getOneNotFound() {
        when(profileRepository.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier
                .create(profileService.getOne("id"))
                .verifyError(UserNotFoundException.class);
    }

    @Test
    void putOne() {
        when(profileRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(profileRepository.save(any())).thenReturn(Mono.just(MockProfiles.mockProfile));

        StepVerifier
                .create(profileService.putOne(MockProfiles.mockProfile))
                .consumeNextWith(profile -> {
                    assertEquals("Unit", profile.getFirstName());
                    assertEquals("Test", profile.getLastName());
                    assertEquals("unit@test.io", profile.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void putOneAlreadyExist() {
        when(profileRepository.findByEmail(anyString())).thenReturn(Mono.just(MockProfiles.mockProfile));

        StepVerifier
                .create(profileService.putOne(MockProfiles.mockProfile))
                .verifyError(UserAlreadyExistException.class);
    }

    @Test
    void deleteOne() {
        when(profileRepository.findById(anyString())).thenReturn(Mono.just(MockProfiles.mockProfile));
        when(profileRepository.deleteById(anyString())).thenReturn(Mono.empty());

        StepVerifier
                .create(profileService.deleteOne("id"))
                .verifyComplete();
    }

    @Test
    void deleteOneNotFound() {
        when(profileRepository.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier
                .create(profileService.deleteOne("id"))
                .verifyError(UserNotFoundException.class);
    }
}
