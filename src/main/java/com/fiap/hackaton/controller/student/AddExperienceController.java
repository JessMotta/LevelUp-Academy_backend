package com.fiap.hackaton.controller.student;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Requisições relacionadas a alunos")
public class AddExperienceController {

    @Autowired
    private StudentService service;

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
    public ResponseEntity<Void> execute(@PathVariable Long studentId, @PathVariable Integer points) {
        log.info("[StudentController] Recebida requisição para adicionar pontos de experiência a um aluno");
        this.service.addExperiencePoints(studentId, points);
        return ResponseEntity.noContent().build();
    }
}
