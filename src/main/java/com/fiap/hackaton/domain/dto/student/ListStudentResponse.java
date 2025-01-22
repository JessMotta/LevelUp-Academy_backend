package com.fiap.hackaton.domain.dto.student;

import com.fiap.hackaton.domain.entity.Student;

public record ListStudentResponse(
        Long id,
        String name
) {
    public ListStudentResponse (Student student) {
        this(
                student.getId(),
                student.getUser().getName()
        );
    }
}
