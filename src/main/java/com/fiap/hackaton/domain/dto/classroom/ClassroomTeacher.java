package com.fiap.hackaton.domain.dto.classroom;

import com.fiap.hackaton.domain.dto.activity.ActivityClassroom;
import com.fiap.hackaton.domain.dto.student.StudentClassroom;
import com.fiap.hackaton.domain.entity.Classroom;

import java.util.List;

public record ClassroomTeacher(
        String subject,
        Integer dayOfWeek,
        Integer timeSpot,
        List<StudentClassroom> students,
        List<ActivityClassroom> activities
) {
    public ClassroomTeacher(Classroom classroom) {
        this(
                classroom.getSubject(),
                classroom.getDayOfWeek(),
                classroom.getTimeSpot(),
                classroom.getStudents()
                        .stream()
                        .map(StudentClassroom::new)
                        .toList(),
                classroom.getActivities()
                        .stream()
                        .map(ActivityClassroom::new)
                        .toList()
        );
    }
}
