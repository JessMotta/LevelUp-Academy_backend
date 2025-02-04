package com.fiap.hackaton.controller.user;

import com.fiap.hackaton.domain.dto.user.UserLogin;
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

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Requisições relacionadas a usuários")
public class LoginController {

    @Autowired
    private UserService service;

    @Operation(summary = "Login do usuário", description = "Requisição para autenticação do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping("login")
    public ResponseEntity<String> execute(@RequestBody @Valid UserLogin login) {
        log.info("[UserController] Recebida requisição de login para o usuário: {}", login.email());
        var response = this.service.login(login);
        log.info("[UserController] Login realizado com sucesso para o usuário: {}", login.email());
        return ResponseEntity.ok(response);
    }
}
