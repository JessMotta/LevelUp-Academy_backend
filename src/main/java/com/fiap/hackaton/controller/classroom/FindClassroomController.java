package com.fiap.hackaton.controller.classroom;

import com.fiap.hackaton.domain.dto.classroom.ClassroomDetails;
import com.fiap.hackaton.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/classroom")
@Tag(name = "Classroom", description = "Requisições relacionadas a salas de aula")
public class FindClassroomController {

    @Autowired
    private ClassroomService service;

    @Operation(summary = "Busca sala de aula por id", description = "Busca sala de aula por id")
    @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala de aula encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula não encontrada")
    })
    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomDetails> execute(@PathVariable Long classroomId) {
        log.info("[ClassroomController] Recebendo requisição para buscar detalhes da sala de aula com ID: {}", classroomId);
        var response = this.service.findClassroomById(classroomId);
        return ResponseEntity.ok(response);
    }
}
