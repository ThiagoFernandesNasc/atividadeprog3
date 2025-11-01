package com.example.demo.dto;

public record UserRequest(
        String nome,
        String email,
        String cep,
        String logradouro,
        String bairro,
        String estado
) {
}
