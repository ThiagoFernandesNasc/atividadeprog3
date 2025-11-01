package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ViaCepResponse(
        String cep,
        String logradouro,
        String bairro,
        String uf
) {
    public AdressResponse toAdressResponse() {
        return new AdressResponse(cep, logradouro, bairro, uf);
    }
}

