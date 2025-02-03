package com.fiap.hackaton.controller.classroom;

import com.fiap.hackaton.service.ClassroomService;
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
@RequestMapping("/classroom")
@Tag(name = "Classroom", description = "Requisições relacionadas a salas de aula")
public class DeleteClassroomController {

    @Autowired
    private ClassroomService service;

    @Operation(summary = "Deleta uma sala de aula", description = "Deleta uma sala de aula")
    @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala de aula deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula não encontrada")
    })
    @DeleteMapping("/{classroomId}")
    public ResponseEntity<Void> execute(@PathVariable Long classroomId) {
        log.info("[ClassroomController] Recebendo requisição para deletar a sala de aula com ID: {}", classroomId);
        this.service.deleteClassroom(classroomId);
        return ResponseEntity.noContent().build();
    }
}
