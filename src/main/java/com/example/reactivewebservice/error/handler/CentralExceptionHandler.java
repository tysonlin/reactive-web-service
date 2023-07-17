package com.example.reactivewebservice.error.handler;

import com.example.reactivewebservice.error.custom.UserAlreadyExistException;
import com.example.reactivewebservice.error.custom.UserNotFoundException;
import com.example.reactivewebservice.error.response.GenericErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class CentralExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericErrorResponse> handleException(WebExchangeBindException e) {
        var errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> Map.of(
                        "field", Objects.requireNonNull(objectError.getObjectName()),
                        "message", Objects.requireNonNull(objectError.getDefaultMessage())))
                .toList();
        return ResponseEntity.badRequest().body(
                GenericErrorResponse.builder()
                        .errors(errors)
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Void> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericErrorResponse> handleUserAlreadyExist(UserAlreadyExistException e) {
        return ResponseEntity.badRequest().body(
                GenericErrorResponse.builder()
                        .errors(List.of(Map.of("message", "User already exist")))
                        .build());
    }

}