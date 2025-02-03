package com.fiap.hackaton.controller.student;

import com.fiap.hackaton.domain.dto.student.StudentResponse;
import com.fiap.hackaton.service.StudentService;
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

@Slf4j
@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Requisições relacionadas a alunos")
public class CreateStudentController {

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
    public ResponseEntity<StudentResponse> execute(@PathVariable Long userId, @RequestParam("grade") String grade, UriComponentsBuilder uriBuilder) {
        log.info("[StudentController] Recebida requisição para criar um novo aluno.");
        var response = this.service.create(userId, grade);
        URI uri = uriBuilder.path("/student/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
