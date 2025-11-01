package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AdressResponse(
        String cep,
        String logradouro,
        String bairro,
        @JsonProperty("estado") String estado
) {
}
