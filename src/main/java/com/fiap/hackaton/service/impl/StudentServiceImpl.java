package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.student.ListStudentResponse;
import com.fiap.hackaton.domain.dto.student.StudentResponse;
import com.fiap.hackaton.domain.entity.Classroom;
import com.fiap.hackaton.domain.entity.Student;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.Roles;
import com.fiap.hackaton.repository.StudentRepository;
import com.fiap.hackaton.service.StudentService;
import com.fiap.hackaton.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;
    private final UserService userService;

    @Override
    public StudentResponse create(Long userId, String grade) {
        log.info("[StudentServiceImpl] Criando estudante para o usuário com ID: {}", userId);
        User user = this.userService.findUserEntityById(userId);

        Student student = Student.builder()
                .user(user)
                .grade(grade)
                .experiencePoints(0)
                .build();
        this.userService.updateRole(userId, Roles.STUDENT);
        student.verifyPatent();

        this.repository.save(student);
        log.info("[StudentServiceImpl] Estudante criado com sucesso com ID: {}", student.getId());

        return new StudentResponse(student);
    }

    @Override
    public StudentResponse findById(Long id) {
        Student student = this.findStudentEntityById(id);
        return new StudentResponse(student);
    }

    @Override
    public List<ListStudentResponse> findAll() {
        log.info("[StudentServiceImpl] Buscando todos os estudantes");
        return this.repository.findAll()
                .stream()
                .map(ListStudentResponse::new)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Student student = this.findStudentEntityById(id);
        this.repository.delete(student);
        log.info("[StudentServiceImpl] Estudante deletado com sucesso com ID: {}", id);
    }

    @Transactional
    @Override
    public void addExperiencePoints(Long id, Integer points) {
        Student student = this.findStudentEntityById(id);

        log.info("[StudentServiceImpl] Adicionando {} pontos de experiência ao estudante com ID: {}", points, id);
        student.addExperiencePoints(points);

        log.info("[StudentServiceImpl] Verificando patente do estudante com ID: {}", id);
        student.verifyPatent();

        this.repository.save(student);
        log.info("[StudentServiceImpl] Salvando alterações do estudante com ID: {}", id);
    }

    @Override
    public Student findStudentEntityById(Long id) {
        log.info("[StudentServiceImpl] Buscando entidade Student com ID: {}", id);
        Student student = this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("[StudentServiceImpl] Estudante com ID: {} não encontrado", id);
                    return new EntityNotFoundException("Estudante não encontrado");
                });
        log.info("[StudentServiceImpl] Estudante encontrado com ID");
        return student;
    }

    @Transactional
    @Override
    public void addStudentToClassroom(Long studentId, Classroom classroom) {
        Student student = this.findStudentEntityById(studentId);
        student.setClassroom(classroom);
    }

    @Transactional
    @Override
    public void removeStudentFromClassroom(Long studentId, Classroom classroom) {
        Student student = this.findStudentEntityById(studentId);
        student.setClassroom(null);
    }
}

