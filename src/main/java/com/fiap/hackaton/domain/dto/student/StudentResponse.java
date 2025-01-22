package com.fiap.hackaton.domain.dto.student;

import com.fiap.hackaton.domain.entity.Student;

public record StudentResponse(
        Long id,
        String name,
        String email,
        Integer experiencePoints,
        String currentPatent
) {
    public StudentResponse (Student student) {
        this(
                student.getId(),
                student.getUser().getName(),
                student.getUser().getEmail(),
                student.getExperiencePoints(),
                student.getCurrentPatent().name()
        );
    }
}
