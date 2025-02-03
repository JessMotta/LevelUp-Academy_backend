package com.fiap.hackaton.controller.activity;

import com.fiap.hackaton.domain.dto.activity.ActivityResponse;
import com.fiap.hackaton.service.ActivityService;
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
@RequestMapping("/activity")
@Tag(name = "Activity", description = "Requisições relacionadas a atividades")
public class FindActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Busca atividade por id", description = "Busca atividade por id")
    @Parameter(name = "activityId", description = "Id da atividade", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @GetMapping("{activityId}")
    public ResponseEntity<ActivityResponse> execute(@PathVariable Long activityId) {
        log.info("[ActivityController] Recebida requisição para buscar uma atividade pelo ID.");
        var response = this.activityService.findActivityById(activityId);
        return ResponseEntity.ok(response);
    }
}
