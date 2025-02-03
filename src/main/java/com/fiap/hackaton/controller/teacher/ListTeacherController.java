package com.fiap.hackaton.controller.teacher;

import com.fiap.hackaton.domain.dto.teacher.ListTeacherResponse;
import com.fiap.hackaton.service.TeacherService;
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
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Requisições relacionadas a professores")
public class ListTeacherController {

    @Autowired
    private TeacherService service;

    @Operation(summary = "Busca todos os professores", description = "Busca todos os professores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professores encontrados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ListTeacherResponse>> execute() {
        log.info("[TeacherController] Recebida requisição para buscar todos os professores.");
        var response = this.service.findAll();
        return ResponseEntity.ok(response);
    }
}
