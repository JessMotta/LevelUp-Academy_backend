package com.fiap.hackaton.domain.dto.activity;

import com.fiap.hackaton.domain.entity.Activity;

public record ActivityClassroom(
        String name,
        String description,
        Integer prestigeValue
) {
    public ActivityClassroom(Activity activity) {
        this(
                activity.getName(),
                activity.getDescription(),
                activity.getPrestigeValue()
        );
    }
}
