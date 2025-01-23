package com.fiap.hackaton.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 255, message = "Nome inválido, deve conter entre 3 e 255 caracteres")
        String name,
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String password
) {
}
