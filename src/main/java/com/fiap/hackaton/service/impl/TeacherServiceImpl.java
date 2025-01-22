package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.teacher.ListTeacherResponse;
import com.fiap.hackaton.domain.dto.teacher.TeacherResponse;
import com.fiap.hackaton.domain.entity.Teacher;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.Roles;
import com.fiap.hackaton.repository.TeacherRepository;
import com.fiap.hackaton.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository repository;
    private final UserService userService;

    @Transactional
    @Override
    public Long create(Long userId) {
        log.info("[TeacherServiceImpl] Criando professor para o usuário com ID: {}", userId);
        User user = this.userService.findUserEntityById(userId);

        Teacher teacher = new Teacher(user);
        log.info("[TeacherServiceImpl] Atualizando o papel do usuário com ID: {} para TEACHER", userId);
        this.userService.updateRole(userId, Roles.TEACHER);

        this.repository.save(teacher);
        log.info("[TeacherServiceImpl] Professor criado com sucesso com ID: {}", teacher.getId());

        return teacher.getId();
    }

    @Override
    public List<ListTeacherResponse> findAll() {
        log.info("[TeacherServiceImpl] Buscando todos os professores");
        return this.repository.findAll()
                .stream()
                .map(ListTeacherResponse::new)
                .toList();
    }

    @Override
    public TeacherResponse findById(Long id) {
        log.info("[TeacherServiceImpl] Buscando professor com ID: {}", id);
        Teacher teacher = this.findTeacherEntityById(id);
        log.info("[TeacherServiceImpl] Professor encontrado com ID: {}", id);
        return new TeacherResponse(teacher);
    }

    @Override
    public Teacher findTeacherEntityById(Long id) {
        log.info("[TeacherServiceImpl] Buscando entidade Teacher com ID: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("[TeacherServiceImpl] Professor com ID: {} não encontrado", id);
                    return new EntityNotFoundException("Professor não encontrado");
                });
    }

    @Override
    public void delete(Long id) {
        log.info("[TeacherServiceImpl] Deletando professor com ID: {}", id);
        Teacher teacher = this.findTeacherEntityById(id);
        this.repository.delete(teacher);
        log.info("[TeacherServiceImpl] Professor com ID: {} deletado com sucesso", id);
    }
}
