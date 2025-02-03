package com.fiap.hackaton.controller;

import com.fiap.hackaton.controller.classroom.*;
import com.fiap.hackaton.domain.dto.classroom.ClassroomDetails;
import com.fiap.hackaton.domain.dto.classroom.ClassroomRequest;
import com.fiap.hackaton.domain.dto.classroom.ListClassroom;
import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.entity.Classroom;
import com.fiap.hackaton.domain.entity.Teacher;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.DayOfWeek;
import com.fiap.hackaton.service.ClassroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassroomControllerTest {

    @Mock
    private ClassroomService classroomService;

    @InjectMocks
    private AddStudentController addStudentController;
    @InjectMocks
    private CreateClassroomController createClassroomController;
    @InjectMocks
    private DeleteClassroomController deleteClassroomController;
    @InjectMocks
    private FindClassroomController findClassroomController;
    @InjectMocks
    private ListClassroomController listClassroomController;
    @InjectMocks
    private RemoveStudentController removeStudentController;
    @InjectMocks
    private UpdateClassroomController updateClassroomController;

    private Classroom classroom;
    private ClassroomRequest classroomRequest;

    @BeforeEach
    public void setUp() {
        UserRequest userRequest = new UserRequest(
                "John Doe",
                "johndoe@example.com",
                "password"
        );

        User user = new User(userRequest);

        Teacher teacher = new Teacher(user);

        classroomRequest = new ClassroomRequest(
                "Matemática",
                DayOfWeek.SEGUNDA_FEIRA,
                1
        );
        classroom = new Classroom(classroomRequest);
        classroom.setTeacher(teacher);
    }

    @Test
    @DisplayName("Test create classroom")
    public void testSave() {
        Long teacherId = 1L;
        Long classroomId = 1L;
        when(classroomService.createClassroom(any(Long.class), any(ClassroomRequest.class))).thenReturn(classroomId);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<Long> response = createClassroomController.execute(teacherId, classroomRequest, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(classroomId, response.getBody());
        assertEquals(URI.create("/classroom/1"), response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Test find classroom by ID")
    public void testFindById() {
        Long classroomId = 1L;
        ClassroomDetails classroomDetails = new ClassroomDetails(classroom);
        when(classroomService.findClassroomById(classroomId)).thenReturn(classroomDetails);

        ResponseEntity<ClassroomDetails> response = findClassroomController.execute(classroomId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(classroomDetails, response.getBody());
    }

    @Test
    @DisplayName("Test list all classrooms")
    public void testListAll() {
        List<ListClassroom> classrooms = Collections.singletonList(new ListClassroom(classroom));
        when(classroomService.listClassrooms()).thenReturn(classrooms);

        ResponseEntity<List<ListClassroom>> response = listClassroomController.execute();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(classrooms, response.getBody());
    }

    @Test
    @DisplayName("Test update classroom")
    public void testUpdate() {
        Long classroomId = 1L;

        ResponseEntity<Void> response = updateClassroomController.execute(classroomId, classroomRequest);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test delete classroom by ID")
    public void testDelete() {
        Long classroomId = 1L;

        ResponseEntity<Void> response = deleteClassroomController.execute(classroomId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test add student to classroom")
    public void testAddStudent() {
        Long classroomId = 1L;
        Long studentId = 1L;

        ResponseEntity<Void> response = addStudentController.execute(classroomId, studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Test remove student from classroom")
    public void testRemoveStudent() {
        Long classroomId = 1L;
        Long studentId = 1L;

        ResponseEntity<Void> response = removeStudentController.execute(classroomId, studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}