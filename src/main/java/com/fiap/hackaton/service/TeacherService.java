package com.fiap.hackaton.service;

import com.fiap.hackaton.domain.dto.teacher.ListTeacherResponse;
import com.fiap.hackaton.domain.dto.teacher.TeacherResponse;
import com.fiap.hackaton.domain.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Long create(Long userId);
    List<ListTeacherResponse> findAll();
    TeacherResponse findById(Long id);
    Teacher findTeacherEntityById(Long id);
    void delete(Long id);
}
