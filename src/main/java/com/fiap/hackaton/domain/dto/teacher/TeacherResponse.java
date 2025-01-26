package com.fiap.hackaton.domain.dto.teacher;

import com.fiap.hackaton.domain.dto.classroom.ClassroomTeacher;
import com.fiap.hackaton.domain.entity.Teacher;

import java.util.List;

public record TeacherResponse(
        Long teacherId,
        String name,
        String email,
        List<ClassroomTeacher> classrooms
) {
    public TeacherResponse(Teacher teacher) {
        this(
                teacher.getId(),
                teacher.getUser().getName(),
                teacher.getUser().getEmail(),
                teacher.getClassrooms()
                        .stream().map(ClassroomTeacher::new)
                        .toList()
        );
    }
}
