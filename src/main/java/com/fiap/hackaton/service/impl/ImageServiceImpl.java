package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.exceptions.ImageException;
import com.fiap.hackaton.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            log.info("[ImageServiceImpl] Buscando diretório para upload");
            Path directory = Paths.get(uploadPath);

            log.info("[ImageServiceImpl] Verificando se o diretório existe");
            if(!Files.exists(directory)) {
                log.info("[ImageServiceImpl] Criando diretório");
                Files.createDirectories(directory);
            }

            log.info("[ImageServiceImpl] Salvando arquivo");
            Path filePath = directory.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            return filePath.getFileName().toString();
        } catch (Exception e) {
            log.error("[ImageServiceImpl] Erro ao fazer upload da imagem", e);
            throw new ImageException("Erro ao fazer upload da imagem");
        }
    }

    @Override
    public Resource downloadImage(String filename) {
        try {
            log.info("[ImageServiceImpl] Buscando arquivo com nome {}", filename);
            Path filePath = Paths.get(uploadPath).resolve(filename).normalize();

            log.info("[ImageServiceImpl] Buscando recurso");
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                log.error("[ImageServiceImpl] Erro ao ler o arquivo");
                throw new ImageException("Erro ao ler o arquivo");
            }

            return resource;
        } catch (Exception e) {
            log.error("[ImageServiceImpl] Erro ao ler o arquivo", e);
            throw new ImageException("Erro ao ler o arquivo");
        }
    }
}
