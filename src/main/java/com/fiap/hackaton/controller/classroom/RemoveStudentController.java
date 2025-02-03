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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/classroom")
@Tag(name = "Classroom", description = "Requisições relacionadas a salas de aula")
public class RemoveStudentController {

    @Autowired
    private ClassroomService service;

    @Operation(summary = "Remove um aluno de uma sala de aula", description = "Remove um aluno")
    @Parameters(value = {
            @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1"),
            @Parameter(name = "studentId", description = "Id do aluno", required = true, example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula ou aluno não encontrado")
    })
    @PatchMapping("/{classroomId}/remove-student/{studentId}")
    public ResponseEntity<Void> execute(@PathVariable Long classroomId, @PathVariable Long studentId) {
        log.info("[ClassroomController] Recebendo requisição para remover o aluno com ID: {} da sala de aula com ID: {}", studentId, classroomId);
        this.service.removeStudent(classroomId, studentId);
        return ResponseEntity.noContent().build();
    }
}
