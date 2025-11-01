package com.example.demo.controller;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //POST http://localost:8080/users
    @PostMapping
    public ResponseEntity<Void> criarUsuario(@RequestBody UserRequest userRequest){
        userService.criarUsuario(userRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET http://localost:8080/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> buscarUsuario(@PathVariable Long id){
        var user = userService.buscarUsuario(id);
        return ResponseEntity.ok(user);
    }

    //GET http://localost:8080/users
    @GetMapping
    public ResponseEntity<List<UserResponse>> buscarUsuarios(){
        var users = userService.buscarUsuarios();
        return ResponseEntity.ok(users);
    }

    //PUT http://localhost:8080/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> atualizarUsuario(@PathVariable Long id, @RequestBody UserRequest userRequest){
        var usuarioAtualizado = userService.atualizarUsuario(id, userRequest);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    //DELETE http://localost:8080/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        userService.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
