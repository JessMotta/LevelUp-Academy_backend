package com.fiap.hackaton.controller.activity;

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

@Slf4j
@RestController
@RequestMapping("/activity")
@Tag(name = "Activity", description = "Requisições relacionadas a atividades")
public class EvaluateActivityController {

    @Autowired
    private ActivityService activityService;

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
    public ResponseEntity<Void> execute(@PathVariable Long activityId, @PathVariable Long studentId, @RequestParam("value") Integer value) {
        log.info("[ActivityController] Recebida requisição para avaliar uma atividade.");
        this.activityService.evaluateActivity(activityId, studentId, value);
        return ResponseEntity.ok().build();
    }
}
