package com.fiap.hackaton.domain.dto.student;

import com.fiap.hackaton.domain.entity.Student;

public record StudentClassroom(
        Long id,
        String name,
        String currentPatent
) {
    public StudentClassroom(Student student) {
        this(
                student.getId(),
                student.getUser().getName(),
                student.getCurrentPatent().name()
        );
    }
}
