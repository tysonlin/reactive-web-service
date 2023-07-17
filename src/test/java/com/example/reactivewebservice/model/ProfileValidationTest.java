package com.example.reactivewebservice.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    void happyCase() {
        Profile profile = Profile.builder()
                .firstName("Unit")
                .middleName("")
                .lastName("Test")
                .email("unit@test.io")
                .build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);

        assertTrue(violations.isEmpty());
    }

    @Test
    void missingRequired() {
        Profile profile = Profile.builder().build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);

        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());
    }

    @Test
    void badEmail() {
        Profile profile = Profile.builder()
                .firstName("Unit")
                .middleName("")
                .lastName("Test")
                .email("totally-valid-email.io")
                .build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }
}
