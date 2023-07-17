package com.example.reactivewebservice.error.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class GenericErrorResponse {

    private List<Map<String, String>> errors;
}
