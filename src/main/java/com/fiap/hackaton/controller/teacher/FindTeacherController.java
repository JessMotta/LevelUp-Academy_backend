package com.fiap.hackaton.controller.teacher;

import com.fiap.hackaton.domain.dto.teacher.TeacherResponse;
import com.fiap.hackaton.service.TeacherService;
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
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Requisições relacionadas a professores")
public class FindTeacherController {

    @Autowired
    private TeacherService service;

    @Operation(summary = "Busca professor por id", description = "Busca professor por id")
    @Parameter(name = "teacherId", description = "Id do professor", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("{teacherId}")
    public ResponseEntity<TeacherResponse> execute(@PathVariable Long teacherId) {
        log.info("[TeacherController] Recebida requisição para buscar professor por id.");
        var response = this.service.findById(teacherId);
        return ResponseEntity.ok(response);
    }
}
