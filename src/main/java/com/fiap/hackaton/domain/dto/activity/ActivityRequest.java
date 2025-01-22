package com.fiap.hackaton.domain.dto.activity;

import com.fiap.hackaton.domain.enums.Execution;
import com.fiap.hackaton.domain.enums.TypeActivity;

public record ActivityRequest(
        String name,
        String description,
        TypeActivity type,
        boolean inGroup,
        Integer studentsPerGroup,
        Execution execution,
        Integer prestigeValue
) {
}
