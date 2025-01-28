package com.fiap.hackaton.domain.dto.classroom;

import com.fiap.hackaton.domain.dto.activity.ActivityClassroom;
import com.fiap.hackaton.domain.dto.student.StudentClassroom;
import com.fiap.hackaton.domain.entity.Classroom;

import java.util.List;

public record ClassroomDetails(
        Long id,
        String subject,
        Integer dayOfWeek,
        Integer timeSpot,
        String teacherName,
        List<StudentClassroom> students,
        List<ActivityClassroom> activities
) {
    public ClassroomDetails(Classroom classroom) {
        this(
                classroom.getId(),
                classroom.getSubject(),
                classroom.getDayOfWeek(),
                classroom.getTimeSpot(),
                classroom.getTeacher().getUser().getName(),
                classroom.getStudents() != null
                        ? classroom.getStudents()
                        .stream()
                        .map(StudentClassroom::new)
                        .toList()
                        : List.of(),
                classroom.getActivities() != null
                        ? classroom.getActivities()
                        .stream()
                        .map(ActivityClassroom::new)
                        .toList()
                        : List.of()
        );
    }
}
