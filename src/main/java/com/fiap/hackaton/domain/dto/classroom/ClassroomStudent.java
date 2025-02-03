package com.fiap.hackaton.domain.dto.classroom;

import com.fiap.hackaton.domain.entity.Classroom;

public record ClassroomStudent(
        Long id,
        String name,
        String teacherName
) {
    public ClassroomStudent(Classroom classroom) {
        this(
                classroom.getId(),
                classroom.getSubject(),
                classroom.getTeacher().getUser().getName()
        );
    }
}
