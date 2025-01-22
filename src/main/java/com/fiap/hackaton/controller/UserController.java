package com.fiap.hackaton.controller;

import com.fiap.hackaton.domain.dto.user.UserLogin;
import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.dto.user.UserResponse;
import com.fiap.hackaton.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Requisições relacionadas a usuários")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Criar usuário", description = "Requisição para criar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping("signup")
    public ResponseEntity<Long> save(@RequestBody @Valid UserRequest request, UriComponentsBuilder uriBuilder) {
        log.info("[UserController] Recebida requisição para criar um novo usuário.");
        var response = this.service.save(request);
        URI uri = uriBuilder
                .path("/user/{id}")
                .buildAndExpand(response)
                .toUri();
        log.info("[UserController] Usuário criado com sucesso com ID: {}", response);
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Login do usuário", description = "Requisição para autenticação do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLogin login) {
        log.info("[UserController] Recebida requisição de login para o usuário: {}", login.email());
        var response = this.service.login(login);
        log.info("[UserController] Login realizado com sucesso para o usuário: {}", login.email());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar usuário por ID", description = "Requisição para buscar um usuário específico pelo ID")
    @Parameter(name = "userId", description = "ID do usuário", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long userId) {
        log.info("[UserController] Recebida requisição para buscar o usuário com ID: {}", userId);
        var user = this.service.findById(userId);
        log.info("[UserController] Usuário encontrado com sucesso: {}", user);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Atualizar usuário", description = "Requisição para atualizar os dados de um usuário")
    @Parameter(name = "userId", description = "ID do usuário", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PutMapping("{userId}")
    public ResponseEntity<Long> update(@PathVariable Long userId, @RequestBody UserRequest request) {
        log.info("[UserController] Recebida requisição para atualizar o usuário com ID: {}", userId);
        var response = this.service.update(userId, request);
        log.info("[UserController] Usuário com ID: {} atualizado com sucesso", userId);
        return ResponseEntity.ok(response);
    }

}
