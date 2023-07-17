package com.example.reactivewebservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class Profile {

    @Id
    private String id;

    @NotNull(message = "First name can not be empty")
    private String firstName;

    private String middleName;

    @NotNull(message = "Last name can not be empty")
    private String lastName;

    @Indexed(background = true, unique = true)
    @NotNull(message = "Email can not be empty")
    @Pattern(regexp = "([a-z])+@([a-z])+\\.([a-z]){2,6}", message = "Invalid email")
    private String email;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
