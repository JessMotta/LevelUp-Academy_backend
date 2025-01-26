package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.classroom.ClassroomDetails;
import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import com.fiap.hackaton.domain.dto.classroom.ListClassroom;
import com.fiap.hackaton.domain.entity.Classroom;
import com.fiap.hackaton.domain.entity.Teacher;
import com.fiap.hackaton.repository.ClassroomRepository;
import com.fiap.hackaton.service.ClassroomService;
import com.fiap.hackaton.service.StudentService;
import com.fiap.hackaton.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository repository;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Transactional
    @Override
    public Long createClassroom(Long teacherId, ClassroomRequest request) {
        log.info("[ClassroomServiceImpl] Criando nova turma para o professor com ID: {}", teacherId);
        Teacher teacher = this.teacherService.findTeacherEntityById(teacherId);

        Classroom classroom = new Classroom(request);
        classroom.setTeacher(teacher);

        this.repository.save(classroom);
        log.info("[ClassroomServiceImpl] Turma criada com sucesso com ID: {}", classroom.getId());

        return classroom.getId();
    }

    @Override
    public List<ListClassroom> listClassrooms() {
        log.info("[ClassroomServiceImpl] Listando todas as turmas");
        return this.repository.findAll()
                .stream()
                .map(ListClassroom::new)
                .toList();
    }

    @Override
    public ClassroomDetails findClassroomById(Long id) {
        log.info("[ClassroomServiceImpl] Buscando detalhes da turma com ID: {}", id);
        Classroom classroom = this.findClassroomEntityById(id);
        return new ClassroomDetails(classroom);
    }

    @Override
    public Classroom findClassroomEntityById(Long id) {
        log.info("[ClassroomServiceImpl] Buscando entidade da turma com ID: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("[ClassroomServiceImpl] Turma com ID: {} não encontrada", id);
                    return new EntityNotFoundException("Classroom not found");
                });
    }

    @Override
    public void updateClassroom(Long id, ClassroomRequest request) {
        log.info("[ClassroomServiceImpl] Atualizando turma com ID: {}", id);
        Classroom classroom = this.findClassroomEntityById(id);
        classroom.update(request);

        this.repository.save(classroom);
        log.info("[ClassroomServiceImpl] Turma com ID: {} atualizada com sucesso", id);
    }

    @Override
    public void deleteClassroom(Long id) {
        log.info("[ClassroomServiceImpl] Excluindo turma com ID: {}", id);
        Classroom classroom = this.findClassroomEntityById(id);
        this.repository.delete(classroom);
        log.info("[ClassroomServiceImpl] Turma com ID: {} excluída com sucesso", id);
    }

    @Transactional
    @Override
    public void addStudent(Long classroomId, Long studentId) {
        log.info("[ClassroomServiceImpl] Adicionando estudante com ID: {} na turma com ID: {}", studentId, classroomId);
        Classroom classroom = this.findClassroomEntityById(classroomId);
        this.studentService.addStudentToClassroom(studentId, classroom);
    }

    @Transactional
    @Override
    public void removeStudent(Long classroomId, Long studentId) {
        log.info("[ClassroomServiceImpl] Removendo estudante com ID: {} da turma com ID: {}", studentId, classroomId);
        Classroom classroom = this.findClassroomEntityById(classroomId);
        this.studentService.removeStudentFromClassroom(studentId, classroom);
    }
}
