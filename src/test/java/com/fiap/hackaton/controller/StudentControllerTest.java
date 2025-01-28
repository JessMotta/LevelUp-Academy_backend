package com.fiap.hackaton.controller;

import com.fiap.hackaton.domain.dto.student.ListStudentResponse;
import com.fiap.hackaton.domain.dto.student.StudentResponse;
import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.entity.Student;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.Patents;
import com.fiap.hackaton.service.StudentService;
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

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        UserRequest request = new UserRequest(
                "John Doe",
                "johndoe@example.com",
                "password"
        );

        User user = new User(request);

        student = Student.builder()
                .id(1L)
                .user(user)
                .experiencePoints(0)
                .currentPatent(Patents.APRENDIZ)
                .build();
    }

    @Test
    @DisplayName("Test create student")
    public void testCreate() {
        Long userId = 1L;
        StudentResponse studentResponse = new StudentResponse(student);
        when(studentService.create(userId)).thenReturn(studentResponse);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<StudentResponse> response = studentController.create(userId, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(studentResponse, response.getBody());
        assertEquals(URI.create("/student/1"), response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Test find student by ID")
    public void testFindById() {
        Long studentId = 1L;
        StudentResponse studentResponse = new StudentResponse(student);
        when(studentService.findById(studentId)).thenReturn(studentResponse);

        ResponseEntity<StudentResponse> response = studentController.findById(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentResponse, response.getBody());
    }

    @Test
    @DisplayName("Test find all students")
    public void testFindAll() {
        List<ListStudentResponse> students = Collections.singletonList(new ListStudentResponse(student));
        when(studentService.findAll()).thenReturn(students);

        ResponseEntity<List<ListStudentResponse>> response = studentController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
    }

    @Test
    @DisplayName("Test delete student by ID")
    public void testDelete() {
        Long studentId = 1L;

        ResponseEntity<Void> response = studentController.delete(studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test add experience points to student")
    public void testAddExperiencePoints() {
        Long studentId = 1L;
        Integer points = 10;

        ResponseEntity<Void> response = studentController.addExperiencePoints(studentId, points);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}