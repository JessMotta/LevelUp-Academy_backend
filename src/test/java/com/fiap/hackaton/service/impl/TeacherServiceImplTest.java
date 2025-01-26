package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.teacher.ListTeacherResponse;
import com.fiap.hackaton.domain.dto.teacher.TeacherResponse;
import com.fiap.hackaton.domain.entity.Teacher;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.Roles;
import com.fiap.hackaton.repository.TeacherRepository;
import com.fiap.hackaton.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository repository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private User user;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        teacher = new Teacher(user);
        teacher.setId(1L);
    }

    @Test
    @DisplayName("Should create teacher for user")
    void create_ShouldCreateTeacherForUser() {
        when(userService.findUserEntityById(user.getId())).thenReturn(user);

        doAnswer(invocation -> {
            Teacher teacher = invocation.getArgument(0);
            teacher.setId(1L);
            return teacher;
        }).when(repository).save(any(Teacher.class));

        Long teacherId = teacherService.create(user.getId());

        assertNotNull(teacherId);
        assertEquals(teacher.getId(), teacherId);
        verify(userService).updateRole(user.getId(), Roles.TEACHER);
        verify(repository).save(any(Teacher.class));
    }

    @Test
    @DisplayName("Should find all teachers")
    void findAll_ShouldReturnAllTeachers() {
        List<Teacher> teachers = List.of(teacher);
        when(repository.findAll()).thenReturn(teachers);

        List<ListTeacherResponse> responses = teacherService.findAll();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    @DisplayName("Should return teacher response when teacher exists")
    void findById_ShouldReturnTeacherResponse_WhenTeacherExists() {
        when(repository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        TeacherResponse response = teacherService.findById(teacher.getId());

        assertNotNull(response);
        assertEquals(teacher.getId(), response.teacherId());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when teacher does not exist")
    void findById_ShouldThrowEntityNotFoundException_WhenTeacherNotExists() {
        when(repository.findById(teacher.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.findById(teacher.getId()));
    }

    @Test
    @DisplayName("Should delete teacher when teacher exists")
    void delete_ShouldDeleteTeacher_WhenTeacherExists() {
        when(repository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        teacherService.delete(teacher.getId());

        verify(repository).delete(teacher);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existing teacher")
    void delete_ShouldThrowEntityNotFoundException_WhenTeacherNotExists() {
        when(repository.findById(teacher.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.delete(teacher.getId()));
    }

}