package com.fiap.hackaton.controller;

import com.fiap.hackaton.controller.student.*;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private AddExperienceController addExperienceController;
    @InjectMocks
    private CreateStudentController createStudentController;
    @InjectMocks
    private DeleteStudentController deleteStudentController;
    @InjectMocks
    private FindStudentController findStudentController;
    @InjectMocks
    private ListStudentsController listStudentsController;

    private Student student;

    @BeforeEach
    public void setUp() {
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
        when(studentService.create(userId, "1º Ano A")).thenReturn(studentResponse);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<StudentResponse> response = createStudentController.execute(userId, "1º Ano A", uriBuilder);

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

        ResponseEntity<StudentResponse> response = findStudentController.execute(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentResponse, response.getBody());
    }

    @Test
    @DisplayName("Test find all students")
    public void testFindAll() {
        List<ListStudentResponse> students = Collections.singletonList(new ListStudentResponse(student));
        when(studentService.findAll()).thenReturn(students);

        ResponseEntity<List<ListStudentResponse>> response = listStudentsController.execute();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
    }

    @Test
    @DisplayName("Test delete student by ID")
    public void testDelete() {
        Long studentId = 1L;

        ResponseEntity<Void> response = deleteStudentController.execute(studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test add experience points to student")
    public void testAddExperiencePoints() {
        Long studentId = 1L;
        Integer points = 10;

        ResponseEntity<Void> response = addExperienceController.execute(studentId, points);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}