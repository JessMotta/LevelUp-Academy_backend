package com.fiap.hackaton.domain.dto.teacher;

import com.fiap.hackaton.domain.entity.Teacher;

public record ListTeacherResponse(
        Long teacherId,
        String name,
        String email
) {
    public ListTeacherResponse(Teacher teacher) {
        this(
                teacher.getId(),
                teacher.getUser().getName(),
                teacher.getUser().getEmail()
        );
    }
}
