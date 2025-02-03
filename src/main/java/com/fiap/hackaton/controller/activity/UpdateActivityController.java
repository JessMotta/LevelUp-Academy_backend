package com.fiap.hackaton.controller.activity;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.service.ActivityService;
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
@RequestMapping("/activity")
@Tag(name = "Activity", description = "Requisições relacionadas a atividades")
public class UpdateActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Atualiza uma atividade", description = "Atualiza uma atividade")
    @Parameter(name = "activityId", description = "Id da atividade", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @PutMapping("{activityId}")
    public ResponseEntity<Long> execute(@PathVariable Long activityId, @RequestBody ActivityRequest request) {
        log.info("[ActivityController] Recebida requisição para atualizar uma atividade.");
        var response = this.activityService.updateActivity(activityId, request);
        return ResponseEntity.ok(response);
    }
}
