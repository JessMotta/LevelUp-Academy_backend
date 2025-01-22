package com.fiap.hackaton.controller;

import com.fiap.hackaton.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Image", description = "Requisições relacionadas a imagens")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Operation(summary = "Busca a imagem salva", description = "Busca a imagem salva no servidor pelo nome do arquivo")
    @Parameter(name = "filename", description = "Nome do arquivo da imagem", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem encontrada"),
            @ApiResponse(responseCode = "404", description = "Imagem não encontrada")
    })
    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) {
        log.info("[ImageController] Buscando imagem com nome {}", filename);
        Resource resource = this.imageService.downloadImage(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename())
                .body(resource);
    }


}
