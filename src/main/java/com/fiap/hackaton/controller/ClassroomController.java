package com.fiap.hackaton.controller;

import com.fiap.hackaton.domain.dto.classroom.ClassroomDetails;
import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import com.fiap.hackaton.domain.dto.classroom.ListClassroom;
import com.fiap.hackaton.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/classroom")
@Tag(name = "Classroom", description = "Requisições relacionadas a salas de aula")
public class ClassroomController {

    @Autowired
    private ClassroomService service;

    @Operation(summary = "Cria uma sala de aula", description = "Cria uma sala de aula")
    @Parameter(name = "teacherId", description = "Id do professor", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala de aula criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @PostMapping("/{teacherId}")
    public ResponseEntity<Long> save(@PathVariable Long teacherId, @RequestBody @Valid ClassroomRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("[ClassroomController] Recebendo requisição para criar uma nova sala de aula para o professor com ID: {}", teacherId);
        var response = this.service.createClassroom(teacherId, request);
        URI uri = uriComponentsBuilder
                .path("/classroom/{id}")
                .buildAndExpand(response)
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Busca sala de aula por id", description = "Busca sala de aula por id")
    @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala de aula encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula não encontrada")
    })
    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomDetails> findById(@PathVariable Long classroomId) {
        log.info("[ClassroomController] Recebendo requisição para buscar detalhes da sala de aula com ID: {}", classroomId);
        var response = this.service.findClassroomById(classroomId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lista todas as salas de aula", description = "Lista todas as salas de aula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas de aula listadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ListClassroom>> listAll() {
        log.info("[ClassroomController] Recebendo requisição para listar todas as salas de aula");
        var response = this.service.listClassrooms();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza uma sala de aula", description = "Atualiza uma sala de aula")
    @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala de aula atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula não encontrada")
    })
    @PutMapping("/{classroomId}")
    public ResponseEntity<Void> update(@PathVariable Long classroomId, @RequestBody ClassroomRequest request) {
        log.info("[ClassroomController] Recebendo requisição para atualizar a sala de aula com ID: {}", classroomId);
        this.service.updateClassroom(classroomId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta uma sala de aula", description = "Deleta uma sala de aula")
    @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala de aula deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula não encontrada")
    })
    @DeleteMapping("/{classroomId}")
    public ResponseEntity<Void> delete(@PathVariable Long classroomId) {
        log.info("[ClassroomController] Recebendo requisição para deletar a sala de aula com ID: {}", classroomId);
        this.service.deleteClassroom(classroomId);
        return ResponseEntity.noContent().build();
    }

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
    public ResponseEntity<Void> addStudent(@PathVariable Long classroomId, @PathVariable Long studentId) {
        log.info("[ClassroomController] Recebendo requisição para adicionar o aluno com ID: {} na sala de aula com ID: {}", studentId, classroomId);
        this.service.addStudent(classroomId, studentId);
        return ResponseEntity.noContent().build();
    }

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
    public ResponseEntity<Void> removeStudent(@PathVariable Long classroomId, @PathVariable Long studentId) {
        log.info("[ClassroomController] Recebendo requisição para remover o aluno com ID: {} da sala de aula com ID: {}", studentId, classroomId);
        this.service.removeStudent(classroomId, studentId);
        return ResponseEntity.noContent().build();
    }

}
