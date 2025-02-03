package com.fiap.hackaton.controller.classroom;

import com.fiap.hackaton.domain.dto.classroom.ListClassroom;
import com.fiap.hackaton.service.ClassroomService;
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
@RequestMapping("/classroom")
@Tag(name = "Classroom", description = "Requisições relacionadas a salas de aula")
public class ListClassroomController {

    @Autowired
    private ClassroomService service;

    @Operation(summary = "Lista todas as salas de aula", description = "Lista todas as salas de aula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas de aula listadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ListClassroom>> execute() {
        log.info("[ClassroomController] Recebendo requisição para listar todas as salas de aula");
        var response = this.service.listClassrooms();
        return ResponseEntity.ok(response);
    }
}
