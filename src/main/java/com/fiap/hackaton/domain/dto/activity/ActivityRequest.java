package com.fiap.hackaton.domain.dto.activity;

import com.fiap.hackaton.domain.enums.Execution;
import com.fiap.hackaton.domain.enums.TypeActivity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActivityRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Type is required")
        TypeActivity type,
        @NotNull(message = "Execution is required")
        boolean inGroup,
        Integer studentsPerGroup,
        @NotNull(message = "Execution is required")
        Execution execution,
        @NotNull(message = "Prestige value is required")
        Integer prestigeValue
) {
}
