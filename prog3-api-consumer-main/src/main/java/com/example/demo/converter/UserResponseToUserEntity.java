package com.example.demo.converter;

import com.example.demo.dto.UserResponse;
import com.example.demo.model.UserModel;

public class UserResponseToUserEntity {

    public static UserModel convert(UserResponse userResponse) {
        String cep = null;
        String logradouro = null;
        String bairro = null;
        String estado = null;
        
        if (userResponse.endereco() != null) {
            cep = userResponse.endereco().cep();
            logradouro = userResponse.endereco().logradouro();
            bairro = userResponse.endereco().bairro();
            estado = userResponse.endereco().estado();
        }
        
        return new UserModel(
                userResponse.id(),
                userResponse.name(),
                userResponse.email(),
                cep,
                logradouro,
                bairro,
                estado
        );
    }
}
