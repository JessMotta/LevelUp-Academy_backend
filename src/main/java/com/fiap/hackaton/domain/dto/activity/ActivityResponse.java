package com.fiap.hackaton.domain.dto.activity;

import com.fiap.hackaton.domain.entity.Activity;
import org.springframework.core.io.Resource;

public record ActivityResponse(
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
