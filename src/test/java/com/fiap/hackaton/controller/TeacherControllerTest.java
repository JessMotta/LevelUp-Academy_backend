package com.fiap.hackaton.controller;

import com.fiap.hackaton.domain.dto.teacher.ListTeacherResponse;
import com.fiap.hackaton.domain.dto.teacher.TeacherResponse;
import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.entity.Teacher;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        UserRequest userRequest = new UserRequest("name", "email@email.com", "password");
        teacher = new Teacher();
        teacher.setUser(new User(userRequest));
    }

    @Test
    @DisplayName("Test create teacher")
    public void testSave() {
        Long userId = 1L;
        Long teacherId = 1L;
        when(teacherService.create(userId)).thenReturn(teacherId);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<Long> response = teacherController.save(userId, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(teacherId, response.getBody());
        assertEquals(URI.create("/teacher/1"), response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Test find all teachers")
    public void testFindAll() {
        List<ListTeacherResponse> teachers = Collections.singletonList(new ListTeacherResponse(teacher));
        when(teacherService.findAll()).thenReturn(teachers);

        ResponseEntity<List<ListTeacherResponse>> response = teacherController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teachers, response.getBody());
    }

    @Test
    @DisplayName("Test find teacher by ID")
    public void testFindById() {
        Long teacherId = 1L;
        TeacherResponse teacherResponse = new TeacherResponse(teacher);
        when(teacherService.findById(teacherId)).thenReturn(teacherResponse);

        ResponseEntity<TeacherResponse> response = teacherController.findById(teacherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherResponse, response.getBody());
    }

    @Test
    @DisplayName("Test delete teacher by ID")
    public void testDelete() {
        Long teacherId = 1L;

        ResponseEntity<Void> response = teacherController.delete(teacherId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}