package com.fiap.hackaton.domain.dto.classroom;

import com.fiap.hackaton.domain.enums.DayOfWeek;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClassroomRequest(
        @NotBlank(message = "Deve ser informado o nome da matéria")
        String subject,
        @NotNull(message = "Deve ser informado o dia da semana")
        DayOfWeek dayOfWeek,
        @NotNull(message = "Deve ser informado o horário")
        Integer timeSpot
) {
}
