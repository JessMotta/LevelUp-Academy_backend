package com.fiap.hackaton.domain.dto.classroom;

import com.fiap.hackaton.domain.entity.Activity;

public record ClassroomActivity(
        Long id,
        String name,
        boolean inGroup,
        Integer studentsPerGroup,
        Integer prestigeValue,
        boolean completed
) {
    public ClassroomActivity(Activity activity) {
        this(
                activity.getId(),
                activity.getName(),
                activity.isInGroup(),
                activity.getStudentsPerGroup(),
                activity.getPrestigeValue(),
                activity.isCompleted()
        );
    }
}
