package com.example.reactivewebservice.repository;

import com.example.reactivewebservice.config.MockProfiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void save() {
        StepVerifier
                .create(
                        profileRepository.deleteAll()
                                .then(profileRepository.save(MockProfiles.mockProfile))
                )
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    void getOne() {
        StepVerifier
                .create(
                        profileRepository.deleteAll()
                                .then(profileRepository.save(MockProfiles.mockProfile))
                                .then(profileRepository.findById(MockProfiles.mockProfile.getId()))
                )
                .consumeNextWith(profile -> {
                    assertEquals("Unit", profile.getFirstName());
                    assertEquals("Test", profile.getLastName());
                    assertEquals("unit@test.io", profile.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void getByEmail() {
        StepVerifier
                .create(
                        profileRepository.deleteAll()
                                .then(profileRepository.save(MockProfiles.mockProfile))
                                .then(profileRepository.findByEmail(MockProfiles.mockProfile.getEmail()))
                )
                .consumeNextWith(profile -> {
                    assertEquals("Unit", profile.getFirstName());
                    assertEquals("Test", profile.getLastName());
                    assertEquals("unit@test.io", profile.getEmail());
                })
                .verifyComplete();
    }
}
