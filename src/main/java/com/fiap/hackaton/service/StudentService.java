package com.fiap.hackaton.service;

import com.fiap.hackaton.domain.dto.student.ListStudentResponse;
import com.fiap.hackaton.domain.dto.student.StudentResponse;
import com.fiap.hackaton.domain.entity.Classroom;
import com.fiap.hackaton.domain.entity.Student;

import java.util.List;

public interface StudentService {
    StudentResponse create(Long userId, String grade);
    StudentResponse findById(Long id);
    List<ListStudentResponse> findAll();
    void delete(Long id);
    void addExperiencePoints(Long id, Integer points);
    Student findStudentEntityById(Long id);
    void addStudentToClassroom(Long studentId, Classroom classroom);
    void removeStudentFromClassroom(Long studentId, Classroom classroom);
}
