package com.fiap.hackaton.controller.activity;

import com.fiap.hackaton.domain.dto.activity.ListActivitiesResponse;
import com.fiap.hackaton.service.ActivityService;
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
@RequestMapping("/activity")
@Tag(name = "Activity", description = "Requisições relacionadas a atividades")
public class ListActivitiesController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Lista todas as atividades", description = "Lista todas as atividades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atividades listadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ListActivitiesResponse>> execute() {
        log.info("[ActivityController] Recebida requisição para listar todas as atividades.");
        var response = this.activityService.listActivities();
        return ResponseEntity.ok(response);
    }
}
