package com.fiap.hackaton.controller.classroom;

import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import com.fiap.hackaton.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
public class UpdateClassroomController {

    @Autowired
    private ClassroomService service;

    @Operation(summary = "Atualiza uma sala de aula", description = "Atualiza uma sala de aula")
    @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala de aula atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula não encontrada")
    })
    @PutMapping("/{classroomId}")
    public ResponseEntity<Void> execute(@PathVariable Long classroomId, @RequestBody ClassroomRequest request) {
        log.info("[ClassroomController] Recebendo requisição para atualizar a sala de aula com ID: {}", classroomId);
        this.service.updateClassroom(classroomId, request);
        return ResponseEntity.noContent().build();
    }
}
