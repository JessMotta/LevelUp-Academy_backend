package com.fiap.hackaton.controller;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.domain.dto.activity.ActivityResponse;
import com.fiap.hackaton.service.ActivityService;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/activity")
@Tag(name = "Activity", description = "Requisições relacionadas a atividades")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Cria uma atividade", description = "Cria uma atividade")
    @Parameter(name = "classroomId", description = "Id da sala de aula", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala de aula não encontrada")
    })
    @PostMapping("{classroomId}")
    public ResponseEntity<Long> create(@PathVariable Long classroomId, @RequestBody ActivityRequest request) {
        log.info("[ActivityController] Recebida requisição para criar uma nova atividade.");
        var response = this.activityService.createActivity(classroomId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca atividade por id", description = "Busca atividade por id")
    @Parameter(name = "activityId", description = "Id da atividade", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @GetMapping("{activityId}")
    public ResponseEntity<ActivityResponse> findById(@PathVariable Long activityId) {
        log.info("[ActivityController] Recebida requisição para buscar uma atividade pelo ID.");
        var response = this.activityService.findActivityById(activityId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza uma atividade", description = "Atualiza uma atividade")
    @Parameter(name = "activityId", description = "Id da atividade", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @PutMapping("{activityId}")
    public ResponseEntity<Long> update(@PathVariable Long activityId, @RequestBody ActivityRequest request) {
        log.info("[ActivityController] Recebida requisição para atualizar uma atividade.");
        var response = this.activityService.updateActivity(activityId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Entrega respostas de uma atividade", description = "Entrega respostas de uma atividade")
    @Parameters(value = {
            @Parameter(name = "activityId", description = "Id da atividade", required = true, example = "1"),
            @Parameter(name = "file", description = "Arquivo com as respostas", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respostas entregues com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @PatchMapping("{activityId}")
    public ResponseEntity<Void> deliverAnswers(@PathVariable Long activityId, @RequestParam("file") MultipartFile file) {
        log.info("[ActivityController] Recebida requisição para entregar respostas de uma atividade.");
        this.activityService.deliverAnswers(activityId, file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Avalia uma atividade", description = "Avalia uma atividade")
    @Parameters(value = {
            @Parameter(name = "activityId", description = "Id da atividade", required = true, example = "1"),
            @Parameter(name = "studentId", description = "Id do aluno", required = true, example = "1"),
            @Parameter(name = "value", description = "Nota da atividade", required = true, example = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade avaliada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @PatchMapping("{activityId}/student/{studentId}")
    public ResponseEntity<Void> evaluateActivity(@PathVariable Long activityId, @PathVariable Long studentId, @RequestParam("value") Integer value) {
        log.info("[ActivityController] Recebida requisição para avaliar uma atividade.");
        this.activityService.evaluateActivity(activityId, studentId, value);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deleta uma atividade", description = "Deleta uma atividade")
    @Parameter(name = "activityId", description = "Id da atividade", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @DeleteMapping("{activityId}")
    public ResponseEntity<Void> delete(@PathVariable Long activityId) {
        log.info("[ActivityController] Recebida requisição para deletar uma atividade.");
        this.activityService.deleteActivity(activityId);
        return ResponseEntity.ok().build();
    }
}
