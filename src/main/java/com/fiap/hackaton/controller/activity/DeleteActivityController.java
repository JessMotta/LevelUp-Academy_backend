package com.fiap.hackaton.controller.activity;

import com.fiap.hackaton.service.ActivityService;
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
@RequestMapping("/activity")
@Tag(name = "Activity", description = "Requisições relacionadas a atividades")
public class DeleteActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Deleta uma atividade", description = "Deleta uma atividade")
    @Parameter(name = "activityId", description = "Id da atividade", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividade deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")
    })
    @DeleteMapping("{activityId}")
    public ResponseEntity<Void> execute(@PathVariable Long activityId) {
        log.info("[ActivityController] Recebida requisição para deletar uma atividade.");
        this.activityService.deleteActivity(activityId);
        return ResponseEntity.ok().build();
    }
}
