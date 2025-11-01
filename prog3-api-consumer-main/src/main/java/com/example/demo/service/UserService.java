package com.example.demo.service;

import com.example.demo.client.AdressClient;
import com.example.demo.dto.AdressResponse;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AdressClient adressClient;

    public void criarUsuario(UserRequest userRequest) {
        var user = new UserModel(
                null,
                userRequest.nome(),
                userRequest.email(),
                userRequest.cep(),
                userRequest.logradouro(),
                userRequest.bairro(),
                userRequest.estado()
        );

        repository.save(user);
    }

    public UserResponse buscarUsuario(Long id) {
        // select * from userModel um where um.id = :id
        return repository.findById(id)
                .map(userModel -> {
                    var endereco = buscarEnderecoUsuario(userModel.getId());
                    return new UserResponse(
                            userModel.getId(),
                            userModel.getName(),
                            userModel.getEmail(),
                            endereco != null ? endereco : new AdressResponse(null, null, null, null)
                    );
                })
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
    }

    public List<UserResponse> buscarUsuarios() {
        return repository.findAll()
                .stream()
                .map(userModel -> {
                    var endereco = buscarEnderecoUsuario(userModel.getId());
                    return new UserResponse(
                            userModel.getId(),
                            userModel.getName(),
                            userModel.getEmail(),
                            endereco != null ? endereco : new AdressResponse(null, null, null, null)
                    );
                })
                .toList();
    }

    private AdressResponse buscarEnderecoUsuario(Long userId) {
        return repository.findById(userId)
                .map(userModel -> {
                    AdressResponse enderecoViaCep = null;
                    
                    // Primeiro, tenta buscar via CEP se tiver
                    if (userModel.getCep() != null && !userModel.getCep().isEmpty()) {
                        try {
                            var viaCepResponse = adressClient.getAdress(userModel.getCep().replaceAll("[^0-9]", ""));
                            enderecoViaCep = viaCepResponse.toAdressResponse();
                        } catch (Exception e) {
                            // Se der erro na busca via CEP, continua com endereço manual
                        }
                    }
                    
                    // Se tiver endereço informado manualmente, usa ele (prioridade)
                    if (userModel.getLogradouro() != null && !userModel.getLogradouro().isEmpty()) {
                        String estado = userModel.getEstado();
                        // Se não tiver estado manual mas tiver do ViaCEP, usa do ViaCEP
                        if ((estado == null || estado.isEmpty()) && enderecoViaCep != null && enderecoViaCep.estado() != null) {
                            estado = enderecoViaCep.estado();
                        }
                        
                        return new AdressResponse(
                                userModel.getCep() != null ? userModel.getCep() : null,
                                userModel.getLogradouro(),
                                userModel.getBairro() != null ? userModel.getBairro() : null,
                                estado != null && !estado.isEmpty() ? estado : null
                        );
                    }
                    
                    // Se não tiver endereço manual, mas tiver resultado do ViaCEP, usa ele
                    if (enderecoViaCep != null) {
                        return enderecoViaCep;
                    }
                    
                    return null;
                })
                .orElse(null);
    }

    public UserResponse atualizarUsuario(Long id, UserRequest userRequest) {
        var userModel = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        
        userModel.setName(userRequest.nome());
        userModel.setEmail(userRequest.email());
        userModel.setCep(userRequest.cep());
        userModel.setLogradouro(userRequest.logradouro());
        userModel.setBairro(userRequest.bairro());
        userModel.setEstado(userRequest.estado());
        
        repository.save(userModel);
        
        // Retorna o usuário atualizado com endereço
        var endereco = buscarEnderecoUsuario(id);
        return new UserResponse(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail(),
                endereco != null ? endereco : new AdressResponse(null, null, null, null)
        );
    }

    public void deletarUsuario(Long id) {
        var userModel = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        repository.delete(userModel);
    }
}
