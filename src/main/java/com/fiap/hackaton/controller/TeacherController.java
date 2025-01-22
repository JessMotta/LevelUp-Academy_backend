package com.fiap.hackaton.controller;

import com.fiap.hackaton.domain.dto.teacher.ListTeacherResponse;
import com.fiap.hackaton.domain.dto.teacher.TeacherResponse;
import com.fiap.hackaton.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Requisições relacionadas a professores")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @Operation(summary = "Cria um professor", description = "Cria um professor")
    @Parameter(name = "userId", description = "Id do usuário", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("{userId}")
    public ResponseEntity<Long> save(@PathVariable Long userId, UriComponentsBuilder uriBuilder) {
        log.info("[TeacherController] Recebida requisição para criar um novo professor.");
        var response = this.service.create(userId);
        URI uri = uriBuilder
                .path("/teacher/{id}")
                .buildAndExpand(response)
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Busca todos os professores", description = "Busca todos os professores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores encontrados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ListTeacherResponse>> findAll() {
        log.info("[TeacherController] Recebida requisição para buscar todos os professores.");
        var response = this.service.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca professor por id", description = "Busca professor por id")
    @Parameter(name = "teacherId", description = "Id do professor", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("{teacherId}")
    public ResponseEntity<TeacherResponse> findById(@PathVariable Long teacherId) {
        log.info("[TeacherController] Recebida requisição para buscar professor por id.");
        var response = this.service.findById(teacherId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deleta professor por id", description = "Deleta professor por id")
    @Parameter(name = "teacherId", description = "Id do professor", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @DeleteMapping("{teacherId}")
    public ResponseEntity<Void> delete(@PathVariable Long teacherId) {
        log.info("[TeacherController] Recebida requisição para deletar professor por id.");
        this.service.delete(teacherId);
        return ResponseEntity.noContent().build();
    }
}
