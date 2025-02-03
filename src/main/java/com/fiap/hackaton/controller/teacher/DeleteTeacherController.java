package com.fiap.hackaton.controller.teacher;

import com.fiap.hackaton.service.TeacherService;
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
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Requisições relacionadas a professores")
public class DeleteTeacherController {

    @Autowired
    private TeacherService service;

    @Operation(summary = "Deleta professor por id", description = "Deleta professor por id")
    @Parameter(name = "teacherId", description = "Id do professor", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @DeleteMapping("{teacherId}")
    public ResponseEntity<Void> execute(@PathVariable Long teacherId) {
        log.info("[TeacherController] Recebida requisição para deletar professor por id.");
        this.service.delete(teacherId);
        return ResponseEntity.noContent().build();
    }
}
