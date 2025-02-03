package com.fiap.hackaton.domain.dto.activity;

import com.fiap.hackaton.domain.entity.Activity;

public record ActivityClassroom(
        Long id,
        String name,
        String description,
        Integer prestigeValue
) {
    public ActivityClassroom(Activity activity) {
        this(
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getPrestigeValue()
        );
    }
}
