package com.fiap.hackaton.controller.user;

import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Requisições relacionadas a usuários")
public class UpdateUserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Atualizar usuário", description = "Requisição para atualizar os dados de um usuário")
    @Parameter(name = "userId", description = "ID do usuário", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PutMapping("{userId}")
    public ResponseEntity<Long> execute(@PathVariable Long userId, @RequestBody UserRequest request) {
        log.info("[UserController] Recebida requisição para atualizar o usuário com ID: {}", userId);
        var response = this.service.update(userId, request);
        log.info("[UserController] Usuário com ID: {} atualizado com sucesso", userId);
        return ResponseEntity.ok(response);
    }
}
