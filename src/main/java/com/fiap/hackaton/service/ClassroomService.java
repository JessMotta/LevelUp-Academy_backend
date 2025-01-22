package com.fiap.hackaton.service;

import com.fiap.hackaton.domain.dto.classroom.ClassroomDetails;
import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import com.fiap.hackaton.domain.dto.classroom.ListClassroom;
import com.fiap.hackaton.domain.entity.Classroom;

import java.util.List;

public interface ClassroomService {
    Long createClassroom(Long teacherId, ClassroomRequest request);
    List<ListClassroom> listClassrooms();
    ClassroomDetails findClassroomById(Long id);
    Classroom findClassroomEntityById(Long id);
    void updateClassroom(Long id, ClassroomRequest request);
    void deleteClassroom(Long id);
    void addStudent(Long classroomId, Long studentId);
    void removeStudent(Long classroomId, Long studentId);


}
