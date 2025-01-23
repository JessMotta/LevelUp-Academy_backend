package com.fiap.hackaton.domain.dto.classroom;

import com.fiap.hackaton.domain.entity.Classroom;

public record ListClassroom(
        Long id,
        String subject,
        Integer dayOfWeek,
        Integer timeSpot
) {
    public ListClassroom(Classroom classroom) {
        this(
                classroom.getId(),
                classroom.getSubject(),
                classroom.getDayOfWeek(),
                classroom.getTimeSpot()
        );
    }
}