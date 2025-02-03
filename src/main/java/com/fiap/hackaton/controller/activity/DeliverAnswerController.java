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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/activity")
@Tag(name = "Activity", description = "Requisições relacionadas a atividades")
public class DeliverAnswerController {

    @Autowired
    private ActivityService activityService;

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
    public ResponseEntity<Void> execute(@PathVariable Long activityId, @RequestParam("file") MultipartFile file) {
        log.info("[ActivityController] Recebida requisição para entregar respostas de uma atividade.");
        this.activityService.deliverAnswers(activityId, file);
        return ResponseEntity.ok().build();
    }
}
