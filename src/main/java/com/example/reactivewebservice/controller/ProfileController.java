package com.example.reactivewebservice.controller;

import com.example.reactivewebservice.model.Profile;
import com.example.reactivewebservice.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping()
    private Mono<ResponseEntity<List<Profile>>> getAll() {
        return profileService.getAll().collectList()
                .map(profiles -> ResponseEntity.ok().body(profiles));
    }

    @GetMapping("/{id}")
    private Mono<ResponseEntity<Profile>> getOne(@PathVariable String id) {
        return profileService.getOne(id)
                .map(profile -> ResponseEntity.ok().body(profile));
    }

    @PutMapping()
    private Mono<ResponseEntity<Profile>> putOne(@Valid @RequestBody Profile profile) {
        return profileService.putOne(profile)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @DeleteMapping("/{id}")
    private Mono<ResponseEntity<Void>> deleteOne(@PathVariable String id) {
        return profileService.deleteOne(id)
                .map(unused -> ResponseEntity.noContent().build());
    }
}
