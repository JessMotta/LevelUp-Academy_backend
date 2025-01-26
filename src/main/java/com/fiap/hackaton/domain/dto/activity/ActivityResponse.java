package com.fiap.hackaton.domain.dto.activity;

import com.fiap.hackaton.domain.entity.Activity;

public record ActivityResponse(
        Long id,
        String name,
        String description,
        String type,
        boolean inGroup,
        Integer studentsPerGroup,
        String execution,
        Integer prestigeValue
) {
    public ActivityResponse(Activity activity) {
        this(
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getType().toString(),
                activity.isInGroup(),
                activity.getStudentsPerGroup(),
                activity.getExecution().name(),
                activity.getPrestigeValue()
        );
    }
}
