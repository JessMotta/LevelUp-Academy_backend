package com.fiap.hackaton.controller.teacher;

import com.fiap.hackaton.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Requisições relacionadas a professores")
public class CreateTeacherController {

    @Autowired
    private TeacherService service;

    @Operation(summary = "Cria um professor", description = "Cria um professor")
    @Parameter(name = "userId", description = "Id do usuário", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("{userId}")
    public ResponseEntity<Long> execute(@PathVariable Long userId, UriComponentsBuilder uriBuilder) {
        log.info("[TeacherController] Recebida requisição para criar um novo professor.");
        var response = this.service.create(userId);
        URI uri = uriBuilder
                .path("/teacher/{id}")
                .buildAndExpand(response)
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
