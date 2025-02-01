package com.fiap.hackaton.domain.dto.student;

import com.fiap.hackaton.domain.dto.classroom.ClassroomStudent;
import com.fiap.hackaton.domain.entity.Student;

import java.util.List;

public record StudentResponse(
        Long id,
        String name,
        String email,
        Integer experiencePoints,
        String currentPatent,
        List<ClassroomStudent> classrooms
) {
    public StudentResponse (Student student) {
        this(
                student.getId(),
                student.getUser().getName(),
                student.getUser().getEmail(),
                student.getExperiencePoints(),
                student.getCurrentPatent().name(),
                student.getClassrooms() != null
                        ? student.getClassrooms()
                        .stream()
                        .map(ClassroomStudent::new)
                        .toList()
                        : List.of()
        );
    }
}
