package com.fiap.hackaton.domain.dto.activity;

import com.fiap.hackaton.domain.entity.Activity;

public record ListActivitiesResponse(
        Long id,
        String type,
        boolean inGroup,
        Integer studentsPerGroup,
        boolean complete,
        Integer prestigeValue
) {
    public ListActivitiesResponse(Activity activity) {
        this(
                activity.getId(),
                activity.getType().name(),
                activity.isInGroup(),
                activity.getStudentsPerGroup(),
                activity.isCompleted(),
                activity.getPrestigeValue()
        );
    }
}
