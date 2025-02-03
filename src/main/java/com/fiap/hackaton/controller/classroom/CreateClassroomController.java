package com.fiap.hackaton.controller.classroom;

import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import com.fiap.hackaton.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/classroom")
@Tag(name = "Classroom", description = "Requisições relacionadas a salas de aula")
public class CreateClassroomController {

    @Autowired
    private ClassroomService service;

    @Operation(summary = "Cria uma sala de aula", description = "Cria uma sala de aula")
    @Parameter(name = "teacherId", description = "Id do professor", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala de aula criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @PostMapping("/{teacherId}")
    public ResponseEntity<Long> execute(@PathVariable Long teacherId, @RequestBody @Valid ClassroomRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("[ClassroomController] Recebendo requisição para criar uma nova sala de aula para o professor com ID: {}", teacherId);
        var response = this.service.createClassroom(teacherId, request);
        URI uri = uriComponentsBuilder
                .path("/classroom/{id}")
                .buildAndExpand(response)
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
