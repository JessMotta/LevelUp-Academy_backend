package com.fiap.hackaton.domain.dto.student;

import com.fiap.hackaton.domain.dto.classroom.ClassroomStudent;
import com.fiap.hackaton.domain.entity.Student;

import java.util.List;

public record StudentResponse(
        Long id,
        String name,
        String grade,
        String email,
        String currentPatent,
        Integer experiencePoints,
        Integer nextPatentExperience,
        List<ClassroomStudent> classrooms
) {
    public StudentResponse (Student student) {
        this(
                student.getId(),
                student.getUser().getName(),
                student.getGrade(),
                student.getUser().getEmail(),
                student.getCurrentPatent().name(),
                student.getExperiencePoints(),
                student.verifyPatent(),
                student.getClassrooms() != null
                        ? student.getClassrooms()
                        .stream()
                        .map(ClassroomStudent::new)
                        .toList()
                        : List.of()
        );
    }
}
