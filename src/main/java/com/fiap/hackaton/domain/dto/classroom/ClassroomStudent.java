package com.fiap.hackaton.domain.dto.classroom;

import com.fiap.hackaton.domain.entity.Classroom;

import java.util.List;

public record ClassroomStudent(
        Long id,
        String name,
        String teacherName,
        List<ClassroomActivity> activities
) {
    public ClassroomStudent(Classroom classroom) {
        this(
                classroom.getId(),
                classroom.getSubject(),
                classroom.getTeacher().getUser().getName(),
                classroom.getActivities()
                        .stream()
                        .map(ClassroomActivity::new)
                        .toList()
        );
    }
}
