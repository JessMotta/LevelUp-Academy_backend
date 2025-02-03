package com.fiap.hackaton.controller.classroom;

import com.fiap.hackaton.service.ClassroomService;
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
@RequestMapping("/classroom")
@Tag(name = "Classroom", description = "Requisições relacionadas a salas de aula")
public class AddStudentController {

    @Autowired
    private ClassroomService service;

    @Operation(summary = "Adiciona um aluno a uma sala de aula", description = "Adiciona um aluno")
    @Parameters(value = {
            @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1"),
            @Parameter(name = "studentId", description = "Id do aluno", required = true, example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno adicionado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula ou aluno não encontrado")
    })
    @PatchMapping("/{classroomId}/add-student/{studentId}")
    public ResponseEntity<Void> execute(@PathVariable Long classroomId, @PathVariable Long studentId) {
        log.info("[ClassroomController] Recebendo requisição para adicionar o aluno com ID: {} na sala de aula com ID: {}", studentId, classroomId);
        this.service.addStudent(classroomId, studentId);
        return ResponseEntity.noContent().build();
    }
}
