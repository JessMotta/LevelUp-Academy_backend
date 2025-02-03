package com.fiap.hackaton.controller.student;

import com.fiap.hackaton.domain.dto.student.ListStudentResponse;
import com.fiap.hackaton.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Requisições relacionadas a alunos")
public class ListStudentsController {

    @Autowired
    private StudentService service;

    @Operation(summary = "Busca todos os alunos", description = "Busca todos os alunos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos encontrados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ListStudentResponse>> execute() {
        log.info("[StudentController] Recebida requisição para buscar todos os alunos.");
        var response = this.service.findAll();
        return ResponseEntity.ok(response);
    }
}
