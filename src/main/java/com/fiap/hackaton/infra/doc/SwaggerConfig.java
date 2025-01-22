package com.fiap.hackaton.infra.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI documentation() {
        return new OpenAPI()
                .info(this.info())
                .servers(List.of(this.serverLocal()));
    }

    private Info info() {
        return new Info()
                .title("LevelUp Academy")
                .description("""
                        LevelUp Academy é uma plataforma de ensino que visa facilitar o aprendizado de crianças e adolescentes
                        através de jogos educativos. A plataforma visa atrair a atenção dos alunos recompensando eles com
                        pontos e dinheiro fictício que podem ser trocados por benefícios decididos pelos professores.
                        """)
                .summary("API REST para EduPlay")
                .version("1.0")
                .contact(this.contact());
    }

    private Contact contact() {
        return new Contact()
                .name("Grupo 01 - Hackaton FIAP - Pós em Full Stack Development")
                .email("support@email.com")
                .url("https://site.com");
    }

    private Server serverLocal() {
        return new Server()
                .url("http://localhost:8001")
                .description("Servidor local");
    }

}
