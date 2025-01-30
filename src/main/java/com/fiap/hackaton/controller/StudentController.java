package com.fiap.hackaton.controller;

import com.fiap.hackaton.domain.dto.student.ListStudentResponse;
import com.fiap.hackaton.domain.dto.student.StudentResponse;
import com.fiap.hackaton.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
@RequestMapping("/student")
@Tag(name = "Student", description = "Requisições relacionadas a alunos")
public class StudentController {

    @Autowired
    private StudentService service;

    @Operation(summary = "Cria um aluno", description = "Cria um aluno")
    @Parameter(name = "userId", description = "Id do usuário", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "404", description = "Sala de aula não encontrada")
    })
    @PostMapping("{userId}")
    public ResponseEntity<StudentResponse> create(@PathVariable Long userId, @RequestParam("grade") String grade, UriComponentsBuilder uriBuilder) {
        log.info("[StudentController] Recebida requisição para criar um novo aluno.");
        var response = this.service.create(userId, grade);
        URI uri = uriBuilder.path("/student/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Busca um aluno pelo ID", description = "Busca um aluno pelo ID")
    @Parameter(name = "studentId", description = "Id do aluno", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @GetMapping("{studentId}")
    public ResponseEntity<StudentResponse> findById(@PathVariable Long studentId) {
        log.info("[StudentController] Recebida requisição para buscar um aluno pelo ID.");
        var response = this.service.findById(studentId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca todos os alunos", description = "Busca todos os alunos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ListStudentResponse>> findAll() {
        log.info("[StudentController] Recebida requisição para buscar todos os alunos.");
        var response = this.service.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deleta um aluno", description = "Deleta um aluno")
    @Parameter(name = "studentId", description = "Id do aluno", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @DeleteMapping("{studentId}")
    public ResponseEntity<Void> delete(@PathVariable Long studentId) {
        log.info("[StudentController] Recebida requisição para deletar um aluno");
        this.service.delete(studentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona pontos de experiência a um aluno", description = "Adiciona pontos de experiência a um aluno")
    @Parameters(value = {
            @Parameter(name = "studentId", description = "Id do aluno", required = true, example = "1"),
            @Parameter(name = "points", description = "Pontos de experiência", required = true, example = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pontos de experiência adicionados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @PatchMapping("{studentId}/points/{points}")
    public ResponseEntity<Void> addExperiencePoints(@PathVariable Long studentId, @PathVariable Integer points) {
        log.info("[StudentController] Recebida requisição para adicionar pontos de experiência a um aluno");
        this.service.addExperiencePoints(studentId, points);
        return ResponseEntity.noContent().build();
    }

}
