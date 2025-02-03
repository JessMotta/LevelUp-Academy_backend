package com.fiap.hackaton.controller.student;

import com.fiap.hackaton.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Requisições relacionadas a alunos")
public class DeleteStudentController {

    @Autowired
    private StudentService service;

    @Operation(summary = "Deleta um aluno", description = "Deleta um aluno")
    @Parameter(name = "studentId", description = "Id do aluno", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @DeleteMapping("{studentId}")
    public ResponseEntity<Void> execute(@PathVariable Long studentId) {
        log.info("[StudentController] Recebida requisição para deletar um aluno");
        this.service.delete(studentId);
        return ResponseEntity.noContent().build();
    }
}
