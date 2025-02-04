package com.fiap.hackaton.controller.user;

import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Requisições relacionadas a usuários")
public class CreateUserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Criar usuário", description = "Requisição para criar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping("signup")
    public ResponseEntity<Long> execute(@RequestBody @Valid UserRequest request, UriComponentsBuilder uriBuilder) {
        log.info("[UserController] Recebida requisição para criar um novo usuário.");
        var response = this.service.save(request);
        URI uri = uriBuilder
                .path("/user/{id}")
                .buildAndExpand(response)
                .toUri();
        log.info("[UserController] Usuário criado com sucesso com ID: {}", response);
        return ResponseEntity.created(uri).body(response);
    }
}
