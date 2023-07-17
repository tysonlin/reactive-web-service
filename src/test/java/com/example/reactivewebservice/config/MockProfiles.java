package com.example.reactivewebservice.config;

import com.example.reactivewebservice.model.Profile;

import java.time.LocalDateTime;

public class MockProfiles {

    public static final Profile mockProfile = Profile.builder()
            .id("12345")
            .firstName("Unit")
            .middleName("T")
            .lastName("Test")
            .email("unit@test.io")
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .build();
}
